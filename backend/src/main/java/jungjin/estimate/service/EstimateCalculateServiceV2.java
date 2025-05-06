package jungjin.estimate.service;

import jungjin.estimate.controller.EstimateCalculate;
import jungjin.estimate.domain.*;
import jungjin.estimate.repository.EstimateCalculateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EstimateCalculateServiceV2 {

    private final EstimateCalculateRepository estimateCalculateRepository;

    public List<Calculate> calculateAndSave(Structure savedStructure) {
        // Just before flush, validate key parts
        if (savedStructure.getStructureDetail() == null) {
            throw new IllegalStateException("StructureDetail is missing.");
        }

        // Step 1: Delete previous calculations
        estimateCalculateRepository.deleteByStructureId(savedStructure.getId());

        StructureDetail savedDetail = savedStructure.getStructureDetail();
        List<Calculate> newCalculations = new ArrayList<>();

        // Step 2: Main structure-based calculations
        List<String> calList = EstimateCalculate.mainCal(savedDetail);
        if (!CollectionUtils.isEmpty(calList)) {
            for (String value : calList) {
                String[] parts = value.split("\\|");
                if (parts.length >= 5) {
                    Calculate cal = new Calculate()
                            .setName(parts[0])
                            .setStandard(parts[1])
                            .setUnit(parts[2])
                            .setAmount((int) Double.parseDouble(parts[3]))
                            .setTotal(Long.parseLong(parts[4]))
                            .setType("P")
                            .setStructure(savedStructure);
                    newCalculations.add(cal);
                }
            }
        }

        // Step 3: Door-related calculations
        if (!CollectionUtils.isEmpty(savedDetail.getDoorList())) {
            for (Door door : savedDetail.getDoorList()) {
                newCalculations.add(mapToDoorCalculate(door, savedStructure));
            }
        }

        // Step 4: Window-related calculations
        if (!CollectionUtils.isEmpty(savedDetail.getWindowList())) {
            for (Window window : savedDetail.getWindowList()) {
                newCalculations.add(mapToWindowCalculate(window, savedStructure));
            }
        }

        // Step 5: Persist one-by-one (no batching)
        List<Calculate> saved = new ArrayList<>();
        for (Calculate c : newCalculations) {
            saved.add(estimateCalculateRepository.save(c));
        }

        return saved;
    }

    private Calculate mapToDoorCalculate(Door door, Structure structure) {
        String subtype = switch (door.getSubType()) {
            case "S" -> "스윙도어";
            case "F" -> "방화문";
            case "H" -> "행거도어(EPS전용)";
            default -> "기타";
        };

        return new Calculate()
                .setName("도어")
                .setStandard(String.format("%d×%d, %s", door.getWidth(), door.getHeight(), subtype))
                .setUnit("EA")
                .setAmount(door.getAmount())
                .setTotal(0L)
                .setType("D")
                .setStructure(structure);
    }

    private Calculate mapToWindowCalculate(Window window, Structure structure) {
        return new Calculate()
                .setName("창호")
                .setStandard(String.format("%d×%d", window.getWidth(), window.getHeight()))
                .setUnit("EA")
                .setAmount(window.getAmount())
                .setTotal(0L)
                .setType("D")
                .setStructure(structure);
    }
}