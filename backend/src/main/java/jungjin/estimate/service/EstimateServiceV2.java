package jungjin.estimate.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jungjin.estimate.domain.Calculate;
import jungjin.estimate.domain.Structure;
import jungjin.estimate.dto.EstimateRequestDTO;
import jungjin.estimate.dto.EstimateResponseDTO;
import jungjin.estimate.mapper.EstimateMapper;
import jungjin.estimate.repository.EstimateRepository;
import jungjin.user.domain.User;
import jungjin.user.service.UserService;
import lombok.RequiredArgsConstructor;
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
    private final UserService userService;

    public EstimateResponseDTO createEstimate(EstimateRequestDTO request) {
        // Convert DTO to entity
        User user = userService.showUser(2L);

        Structure structure = estimateMapper.toStructure(request, user);

        // Save structure with details and subcomponents
        Structure savedStructure = estimateRepository.save(structure);
        List<Calculate> calculates = estimateCalculateService.calculateAndSave(savedStructure);
        structure.setCalculateList(calculates);

        return estimateMapper.toResponseDTO(savedStructure);
    }
}