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
public class EstimateCalculateService {

    private final EstimateCalculateRepository estimateCalculateRepository;
    private final EstimatePriceService estimatePriceService;

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
        int index = 0;
        if (!CollectionUtils.isEmpty(calList)) {
            for (String value : calList) {
                String[] parts = value.split("\\|");
                Calculate cal = new Calculate()
                        .setName(parts[0])
                        .setStandard(parts[1])
                        .setUnit(parts[2])
                        .setAmount((int) Double.parseDouble(parts[3]))
                        .setTotal(Long.parseLong(parts[4]))
                        .setEPrice(Integer.parseInt (parts[5]))
                        .setUPrice(Integer.parseInt (parts[6]))
                        .setSort(index++);
                newCalculations.add(cal);
            }
        }

        // Step 3: Door-related calculations
        if (!CollectionUtils.isEmpty(savedDetail.getDoorList())) {
            for (Door door : savedDetail.getDoorList()) {
                newCalculations.add(mapToDoorCalculate(door, savedStructure, index++));
            }
        }

        // Step 4: Window-related calculations
        if (!CollectionUtils.isEmpty(savedDetail.getWindowList())) {
            for (Window window : savedDetail.getWindowList()) {
                newCalculations.add(mapToWindowCalculate(window, savedStructure, index++));
            }
        }

        // Step 5: Persist one-by-one (no batching)
        List<Calculate> saved = new ArrayList<>();
        for (Calculate c : newCalculations) {
            c.setStructure(savedStructure);
            saved.add(estimateCalculateRepository.save(c));
        }

        return saved;
    }

    private Calculate mapToDoorCalculate(Door door, Structure structure, int index) {
        int width = door.getWidth();
        int height = door.getHeight();
        int amount = door.getAmount();
        String subType = door.getSubType();
        String standard = "";
        String thick = String.valueOf(structure.getStructureDetail().getOutsideWallThick());

        String dimension = width + "*" + height;
        Price price = null;

        standard = switch (subType) {
            case "S" -> {
                price = estimatePriceService.showPrice("D", "S", thick + "티판넬용", dimension);
                yield "스윙도어";
            }
            case "F" -> {
                price = estimatePriceService.showPrice("D", "F", thick + "티판넬용", dimension);
                yield "방화문";
            }
            case "H" -> {
                price = estimatePriceService.showPrice("D", "H", "마감" + thick + "티", "");
                yield "행거도어(EPS전용)";
            }
            default -> "기타 도어";
        };

        int unitPrice = price != null ? price.getStartPrice() : 0;
        long total = (long) unitPrice * amount;

        return new Calculate()
                .setName("도어")
                .setStandard(String.format("%d×%d, %s", width, height, standard))
                .setUnit("EA")
                .setAmount(amount)
                .setTotal(total)
                .setType("D")
                .setEPrice(0)
                .setUPrice(unitPrice)
                .setSort(index);
    }

    private Calculate mapToWindowCalculate(Window window, Structure structure, int index) {
        int width = window.getWidth();
        int height = window.getHeight();
        int amount = window.getAmount();
        String type = window.getType();
        String wallThick = String.valueOf(structure.getStructureDetail().getOutsideWallThick());

        Price price = switch (type) {
            case "S" -> estimatePriceService.showPrice("W", "S", wallThick + "티판넬용", "16mm유리");
            case "D" -> estimatePriceService.showPrice("W", "D", "225T", "16mm유리");
            default -> null;
        };

        int unitPrice = price != null ? price.getStartPrice() : 0;
        long total = (long) unitPrice * amount;

        return new Calculate()
                .setName("창호")
                .setStandard(String.format("%d×%d", width, height))
                .setUnit("EA")
                .setAmount(amount)
                .setTotal(total)
                .setType("D")
                .setEPrice(0)
                .setUPrice(unitPrice)
                .setSort(index);
    }

}