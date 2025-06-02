package jungjin.estimate.service;

import jakarta.persistence.EntityNotFoundException;
import jungjin.admin.dto.StatDTO;
import jungjin.estimate.domain.Calculate;
import jungjin.estimate.domain.Structure;
import jungjin.estimate.dto.EstimateRequestDTO;
import jungjin.estimate.dto.EstimateResponseDTO;
import jungjin.estimate.mapper.EstimateMapper;
import jungjin.estimate.repository.EstimateRepository;
import jungjin.user.domain.User;
import jungjin.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EstimateService {

    private final EstimateMapper estimateMapper;
    private final EstimateRepository estimateRepository;
    private final EstimateCalculateService estimateCalculateService;
    private final UserService userService;

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
        //TODO: need to setup get userId via token
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

    public StatDTO getStats() {
        long total = estimateRepository.count();
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
        long todayCount = estimateRepository.countByCreateDateBetween(startOfDay, endOfDay);
        return StatDTO.builder().total(total).today(todayCount).build();
    }
}