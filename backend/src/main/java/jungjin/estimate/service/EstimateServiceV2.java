package jungjin.estimate.service;

import jakarta.persistence.EntityNotFoundException;
import jungjin.estimate.domain.Calculate;
import jungjin.estimate.domain.Structure;
import jungjin.estimate.dto.CalculateDTO;
import jungjin.estimate.dto.EstimateRequestDTO;
import jungjin.estimate.dto.EstimateResponseDTO;
import jungjin.estimate.mapper.EstimateMapper;
import jungjin.estimate.repository.EstimateRepository;
import jungjin.user.domain.User;
import jungjin.user.dto.UserResponseDTO;
import jungjin.user.service.UserService;
import jungjin.user.service.UserV2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EstimateServiceV2 {

    private final EstimateMapper estimateMapper;
    private final EstimateRepository estimateRepository;
    private final EstimateCalculateServiceV2 estimateCalculateService;
    private final UserV2Service userService;

    public Page<EstimateResponseDTO> getUserEstimates(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        //TODO: need to setup get userId via token
        Long userId = 2L;
        Page<Structure> structures = estimateRepository.findByUserNum(userId, pageable);
        return structures.map(estimateMapper::toResponseDTO);
    }

    public Page<EstimateResponseDTO> getAllEstimates(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Structure> structures = estimateRepository.findAll(pageable);
        return structures.map(estimateMapper::toResponseDTO);
    }

    public EstimateResponseDTO createEstimate(EstimateRequestDTO request) {
        // Convert DTO to entity
        User user = userService.getUserByUserNumReturnUser(2L);

        Structure structure = estimateMapper.toStructure(request, user);

        // Save structure with details and subcomponents
        Structure savedStructure = estimateRepository.save(structure);
        List<Calculate> calculates = estimateCalculateService.calculateAndSave(savedStructure);
        savedStructure.setCalculateList(calculates);

        return estimateMapper.toResponseDTO(savedStructure);
    }

    public EstimateResponseDTO getEstimateById(Long id) {
        Structure structure = estimateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("structure not found"));

        return estimateMapper.toResponseDTO(structure);
    }

    public EstimateResponseDTO updateEstimate(Long id, EstimateRequestDTO request) {
        Structure existing = estimateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Structure not found with id: " + id));

        Structure updated = estimateMapper.updateEntity(existing, request);
        Structure saved = estimateRepository.save(updated);
        List<Calculate> calculates = estimateCalculateService.calculateAndSave(updated);
        saved.setCalculateList(calculates);

        return estimateMapper.toResponseDTO(saved);
    }

    @Transactional
    public void deleteEstimate(Long id) {
        Structure structure = estimateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Structure not found with id: " + id));
        estimateRepository.delete(structure);
    }

}