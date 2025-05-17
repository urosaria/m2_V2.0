package jungjin.estimate.controller;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

import jungjin.config.UploadConfig;
import jungjin.estimate.dto.EstimateListDTO;
import jungjin.estimate.mapper.EstimateMapper;
import jungjin.user.service.UserService;
import org.springframework.core.io.Resource;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import jakarta.servlet.http.HttpServletRequest;
import jungjin.estimate.domain.*;
import jungjin.estimate.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.nociar.jpacloner.JpaCloner;

@RestController
@RequestMapping("/api/estimates/old")
@RequiredArgsConstructor
public class EstimateController {

    private final EstimateService estimateService;
    private final EstimateDetailService estimateDetailService;
    private final EstimatePriceService estimatePriceService;
    private final UploadConfig uploadConfig;
    private final UserService userService;
    private final EstimateMapper estimateMapper;

    @PersistenceContext
    private EntityManager entityManager;

    final String pateTitle = "자동판넬견적";

    @GetMapping("/list")
    public ResponseEntity<?> getEstimateList(@RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        Long userNum = 2L;
        Page<Structure> listEstimate = estimateService.listEstimate(page, size, userNum);

        List<EstimateListDTO> estimateDTOs = listEstimate.getContent().stream()
                .map(EstimateListDTO::from)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("page", page);
        response.put("size", size);
        response.put("totalPages", listEstimate.getTotalPages());
        response.put("totalElements", listEstimate.getTotalElements());
        response.put("estimates", estimateDTOs);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/list/contents")
    public ResponseEntity<?> getBoardContents(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "7") int size) {
        Long userNum = 2L; // For testing purposes
        Page<Structure> listEstimate = estimateService.listEstimate(page, size, userNum);
        
        List<EstimateListDTO> estimateDTOs = listEstimate.getContent().stream()
                .map(EstimateListDTO::from)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("page", page);
        response.put("size", size);
        response.put("totalPages", listEstimate.getTotalPages());
        response.put("totalElements", listEstimate.getTotalElements());
        response.put("estimates", estimateDTOs);

        return ResponseEntity.ok(response);
    }
// seems like
//    @PostMapping("/register")
//    public ResponseEntity<?> createEstimate(
//            @RequestBody EstimateRequestDTO request,
//            @RequestParam(defaultValue = "") String mode) {
//        try {
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            User user = (User) auth.getPrincipal();
////            if (mode.isEmpty()) {
////                //structure.setUser(user);
////            }
//            Structure structure = estimateMapper.toStructure(request);
//            if (mode.isEmpty()) {
//                structure.setUser(user);
//            }
//
//            Structure result = estimateService.saveEstimate(structure);
//            if (result == null) {
//                return ResponseEntity.badRequest().body("Failed to save estimate");
//            }
//
//            StructureDetail structureDetail = estimateMapper.toStructureDetail(request);
//            structureDetail.setStructure(result);
//
//            structureDetail = structure.getStructureDetail();
//            structureDetail.setStructure(result);
//            if (structureDetail.getInsideWallYn().equals("N")) {
//                structureDetail.setInsideWallType("");
//                structureDetail.setInsideWallPaper("");
//                structureDetail.setInsideWallThick(0);
//            }
//            if (structureDetail.getCeilingYn().equals("N")) {
//                structureDetail.setCeilingType("");
//                structureDetail.setCeilingPaper("");
//                structureDetail.setCeilingThick(0);
//            }
//            estimateDetailService.saveEstimateDetail(structureDetail);
//            estimateDetailService.saveEstimateDetailEtc(structureDetail);
//            List<String> calList = EstimateCalculate.mainCal(structureDetail);
//            if (calList.size() > 0) {
//                estimateService.deleteCal(Long.valueOf(result.getId()));
//                int index = 0;
//                for (String value : calList) {
//                    Calculate calculate = new Calculate();
//                    String[] calculateArray = value.split("\\|");
//                    calculate.setName(calculateArray[0]);
//                    calculate.setStandard(calculateArray[1]);
//                    calculate.setUnit(calculateArray[2]);
//                    calculate.setAmount((int) Double.parseDouble(calculateArray[3]));
//                    calculate.setTotal(Long.valueOf(calculateArray[4]).longValue());
//                    calculate.setEPrice(Integer.parseInt(calculateArray[5]));
//                    calculate.setUPrice(Integer.parseInt(calculateArray[6]));
//                    calculate.setStructure(result);
//                    calculate.setSort(index++);
//                    estimateService.saveCal(calculate);
//                }
//                if (structureDetail.getDoorYn().equals("Y")) {
//                    int doorListCount = structureDetail.getDoorList().size();
//                    for (int i = 0; i < doorListCount; i++) {
//                        Calculate calculate = new Calculate();
//                        int w = ((Door) structureDetail.getDoorList().get(i)).getWidth();
//                        int h = ((Door) structureDetail.getDoorList().get(i)).getHeight();
//                        int e = ((Door) structureDetail.getDoorList().get(i)).getAmount();
//                        String st = ((Door) structureDetail.getDoorList().get(i)).getSubType();
//                        String standard = "";
//                        Price price = new Price();
//                        String thick = String.valueOf(structureDetail.getOutsideWallThick());
//                        if (st.equals("S")) {
//                            price = estimatePriceService.showPrice("D", "S", thick + "티판넬용", String.valueOf(w) + "*" + String.valueOf(h));
//                            standard = "스윙도어";
//                        } else if (st.equals("F")) {
//                            price = estimatePriceService.showPrice("D", "F", thick + "티판넬용", String.valueOf(w) + "*" + String.valueOf(h));
//                            standard = "방화문";
//                        } else if (st.equals("H")) {
//                            price = estimatePriceService.showPrice("D", "H", "마감" + thick + "티", "");
//                            standard = "행거도어(EPS전용)";
//                        }
//                        int startPrice = 0, gapPrice = 0, maxPrice = 0, ePrice = 0, uPrice = 0;
//                        long total = 0L;
//                        if (price != null) {
//                            startPrice = price.getStartPrice();
//                            total = (startPrice * e);
//                            uPrice = startPrice;
//                        }
//                        calculate.setName("도어");
//                        calculate.setStandard(String.valueOf(w) + "*" + String.valueOf(h) + "," + standard);
//                        calculate.setUnit("EA");
//                        calculate.setAmount(e);
//                        calculate.setTotal(total);
//                        calculate.setStructure(result);
//                        calculate.setType("D");
//                        calculate.setEPrice(0);
//                        calculate.setUPrice(uPrice);
//                        calculate.setSort(index++);
//                        estimateService.saveCal(calculate);
//                    }
//                }
//                if (structureDetail.getWindowYn().equals("Y")) {
//                    int windowListCount = structureDetail.getWindowList().size();
//                    for (int i = 0; i < windowListCount; i++) {
//                        Calculate calculate = new Calculate();
//                        int w = ((Window) structureDetail.getWindowList().get(i)).getWidth();
//                        int h = ((Window) structureDetail.getWindowList().get(i)).getHeight();
//                        int e = ((Window) structureDetail.getWindowList().get(i)).getAmount();
//                        String type = ((Window) structureDetail.getWindowList().get(i)).getType();
//                        Price price = new Price();
//                        String thick = String.valueOf(structureDetail.getOutsideWallThick());
//                        if (type.equals("S")) {
//                            price = estimatePriceService.showPrice("W", "S", thick + "티판넬용", "16mm유리");
//                        } else if (type.equals("D")) {
//                            price = estimatePriceService.showPrice("W", "D", "225T", "16mm유리");
//                        }
//                        int startPrice = 0, gapPrice = 0, maxPrice = 0, uPrice = 0;
//                        long total = 0L;
//                        if (price != null) {
//                            startPrice = price.getStartPrice();
//                            total = (startPrice * e);
//                            uPrice = startPrice;
//                        }
//                        calculate.setName("창호");
//                        calculate.setStandard(String.valueOf(w) + "*" + String.valueOf(h));
//                        calculate.setUnit("EA");
//                        calculate.setAmount(e);
//                        calculate.setTotal(total);
//                        calculate.setStructure(result);
//                        calculate.setType("D");
//                        calculate.setEPrice(0);
//                        calculate.setUPrice(uPrice);
//                        calculate.setSort(index++);
//                        estimateService.saveCal(calculate);
//                    }
//                }
//                estimateService.excel("", Long.valueOf(result.getId()));
//            }
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("id", result.getId());
//            response.put("message", "Estimate created successfully");
//
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error creating estimate: " + e.getMessage());
//        }
//    }

    @GetMapping("/calculate/{id}")
    public ResponseEntity<?> calculateById(@PathVariable("id") Long id,
                                           @RequestParam(name = "mode", defaultValue = "") String mode
    ) {
        Map<String, Object> response = new HashMap<>();
        StructureDetail structureDetail = new StructureDetail();

        if (id != null) {
            StructureDetail detail = estimateDetailService.showEstimateDetailId(id);

            if (detail != null) {
                int canopyCount = detail.getCanopyList().size();
                int ceilingCount = detail.getCeilingList().size();
                int doorCount = detail.getDoorList().size();
                int insideWallCount = detail.getInsideWallList().size();
                int windowCont = detail.getWindowList().size();
                List<Calculate> doorPrice = estimateService.findByStructureIdAndType(Long.valueOf(detail.getStructure().getId()));
                if (canopyCount > 0) {
                    int canopyTotal = 0;
                    for (int i = 0; i < canopyCount; i++)
                        canopyTotal += ((Canopy) detail.getCanopyList().get(i)).getAmount();
                    response.put("canopyTotal", Integer.valueOf(canopyTotal));
                }
                if (ceilingCount > 0) {
                    int ceilingTotal = 0;
                    for (int i = 0; i < ceilingCount; i++)
                        ceilingTotal += ((Ceiling) detail.getCeilingList().get(i)).getAmount();
                    response.put("ceilingTotal", Integer.valueOf(ceilingTotal));
                }
                if (doorCount > 0) {
                    int doorTotal = 0;
                    for (int i = 0; i < doorCount; i++)
                        doorTotal += ((Door) detail.getDoorList().get(i)).getAmount();
                    response.put("doorTotal", Integer.valueOf(doorTotal));
                }
                if (insideWallCount > 0) {
                    int insideWallTotal = 0;
                    for (int i = 0; i < insideWallCount; i++)
                        insideWallTotal += ((InsideWall) detail.getInsideWallList().get(i)).getAmount();

                    response.put("insideWallTotal", Integer.valueOf(insideWallTotal));
                }
                if (windowCont > 0) {
                    int windowTotal = 0;
                    for (int i = 0; i < windowCont; i++)
                        windowTotal += ((Window) detail.getWindowList().get(i)).getAmount();
                    response.put("windowTotal", Integer.valueOf(windowTotal));
                }
                // TODO: verify this
                structureDetail = detail;
                response.put("doorPriceCount", Integer.valueOf(doorPrice.size()));
            }
        }
        response.put("structureDetail", structureDetail);
        response.put("mode", mode);
        return ResponseEntity.ok(response);
    }

    // TODO: need to change hard delete not soft delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeStructure(@PathVariable Long id) {
        try {
            estimateService.updateStructureStatus(id, "D");
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fail");
        }
    }

    @PutMapping("/{id}/state/{state}")
    public ResponseEntity<String> updateStructureState(@PathVariable Long id, @PathVariable String state) {
        try {
            estimateService.updateStructureStatus(id, state);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fail");
        }
    }

    @PostMapping("/{id}/copy")
    public ResponseEntity<String> copyStructure(@PathVariable Long id) {
        try {
            Structure structure = estimateService.showEstimateCopy(id);
            structure.setId(0L);
            structure.setPlaceName("[복사본]" + structure.getPlaceName());
            structure.setCalculateList(null);

            Structure result = estimateService.saveEstimate(structure);
            if (result != null) {
                // Copy Calculate list
                List<Calculate> calList = estimateService.listCal(id);
                calList = JpaCloner.clone(calList, new String[]{"*"});
                for (Calculate calculate : calList) {
                    calculate.setStructure(result);
                    calculate.setId(0L);
                    estimateService.saveCal(calculate);
                }

                // Copy Structure Detail
                StructureDetail structureDetail = estimateDetailService.showEstimateDetailCopy(id);
                Long oldDetailId = structureDetail.getId();
                structureDetail.setId(0L);
                structureDetail.setStructure(result);
                structureDetail.setCanopyList(null);

                StructureDetail detailResult = estimateDetailService.saveEstimateDetail(structureDetail);
                estimateDetailService.copyEstimateDetailEtc(oldDetailId, detailResult);

                // Copy Excel
                estimateService.excelCopy(id, result.getId());
            }

            return ResponseEntity.ok("success");
        } catch (Exception e) {
            System.err.println("Structure copy failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fail");
        }
    }

    @GetMapping("/file/{bno}/download")
    public ResponseEntity<Resource> downloadEstimateFile(
            @PathVariable Long bno,
            @RequestParam(value = "type", defaultValue = "user") String type,
            HttpServletRequest request
    ) throws UnsupportedEncodingException {
        try {
            // File metadata
            String fileName = "estimate" + bno + ".xlsx";
            String filePath = uploadConfig + "/estimate/" + fileName;
            File file = new File(filePath);

            if (!file.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            Structure structure = estimateService.showEstimate(bno);
            String displayName = structure.getPlaceName() + ".xlsx";

            // Content-Type detection
            String contentType = request.getServletContext().getMimeType(file.getAbsolutePath());
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            // Proper encoding for filename based on browser
            String encodedFileName;
            String userAgent = request.getHeader("User-Agent");

            if (userAgent != null && (userAgent.contains("MSIE") || userAgent.contains("Trident") || userAgent.contains("Edge"))) {
                encodedFileName = URLEncoder.encode(displayName, "UTF-8").replaceAll("\\+", " ");
            } else {
                encodedFileName = new String(displayName.getBytes("UTF-8"), "ISO-8859-1");
            }

            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()))
                    .body(resource);

        } catch (Exception e) {
            System.err.println("File download error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // TODO: originally divided into 4 steps, but combined as one
//    @PostMapping
//    public ResponseEntity<?> createEstimate(@RequestBody EstimateRequestDTO request) {
//        try {
//            // STEP 01: Save Structure and associate user
//            UserCustom principal = (UserCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            Structure structure = estimateMapper.toStructure(request);
//            //structure.setUser(principal.getUser());
//            Structure savedStructure = estimateService.saveEstimate(structure);
//
//            if (savedStructure == null) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save structure");
//            }
//
//            // STEP 02: Retrieve or initialize StructureDetail, then apply step 2 logic
//            StructureDetail detailInput = estimateMapper.toStructureDetail(request);
//            StructureDetail detail = estimateDetailService.showEstimateDetailId(detailInput.getId());
//            if (detail == null) {
//                detail = new StructureDetail();
//                detail.setStructure(savedStructure);
//            }
//            detail.updateStep2(detailInput);
//
//            // STEP 03: Apply step 3 logic (gucci update and save ETC data)
//            detail.updateGucci(detailInput);
//            estimateDetailService.saveEstimateDetail(detail); // optional intermediate save
//            StructureDetail savedDetail = estimateDetailService.saveEstimateDetailEtc(detailInput);
//            savedDetail.setStructure(savedStructure);
//
//            // STEP 04: Apply step 4 logic (update + calculations)
//            savedDetail.updateStep4(detailInput);
//            savedDetail = estimateDetailService.saveEstimateDetail(savedDetail);
//
//            // Recalculate items and save
//            List<String> calList = EstimateCalculate.mainCal(savedDetail);
//            if (!calList.isEmpty()) {
//                estimateService.deleteCal(savedStructure.getId());
//
//                for (String value : calList) {
//                    String[] parts = value.split("\\|");
//                    Calculate cal = new Calculate();
//                    cal.setName(parts[0]);
//                    cal.setStandard(parts[1]);
//                    cal.setUnit(parts[2]);
//                    cal.setAmount((int) Double.parseDouble(parts[3]));
//                    cal.setTotal(Integer.parseInt(parts[4]));
//                    cal.setStructure(savedStructure);
//                    estimateService.saveCal(cal);
//                }
//
//                // Door → Calculate
//                for (Door door : savedDetail.getDoorList()) {
//                    Calculate cal = new Calculate();
//                    String subtype = switch (door.getSubType()) {
//                        case "S" -> "스윙도어";
//                        case "F" -> "방화문";
//                        case "H" -> "행거도어(EPS전용)";
//                        default -> "기타";
//                    };
//                    cal.setName("도어");
//                    cal.setStandard(door.getWidth() + "*" + door.getHeight() + "," + subtype);
//                    cal.setUnit("EA");
//                    cal.setAmount(door.getAmount());
//                    cal.setTotal(0L);
//                    cal.setStructure(savedStructure);
//                    cal.setType("D");
//                    estimateService.saveCal(cal);
//                }
//
//                // Window → Calculate
//                for (Window window : savedDetail.getWindowList()) {
//                    Calculate cal = new Calculate();
//                    cal.setName("창호");
//                    cal.setStandard(window.getWidth() + "*" + window.getHeight());
//                    cal.setUnit("EA");
//                    cal.setAmount(window.getAmount());
//                    cal.setTotal(0L);
//                    cal.setStructure(savedStructure);
//                    cal.setType("D");
//                    estimateService.saveCal(cal);
//                }
//            }
//
//            // Final result
//            return ResponseEntity.ok(Map.of(
//                    "structureId", savedStructure.getId(),
//                    "structureDetailId", savedDetail.getId(),
//                    "message", "Estimate saved successfully"
//            ));
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Failed to save estimate: " + e.getMessage());
//        }
//    }

    @GetMapping("/step05-summary")
    public ResponseEntity<?> getEstimateStep05Summary(
            @RequestParam(name = "structure_detail_id", required = false) Long structureDetailId) {

//        if (structureDetailId == null) {
//            return ResponseEntity.badRequest().body("structure_detail_id is required");
//        }
//
//        StructureDetail detail = estimateDetailService.showEstimateDetailId(structureDetailId);
//        if (detail == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("StructureDetail not found");
//        }
//
//        EstimateResponseDTO dto = new EstimateResponseDTO();
//
//        // Count canopy
//        if (detail.getCanopyList() != null) {
//            dto.setCanopyTotal(detail.getCanopyList().stream()
//                    .mapToInt(Canopy::getAmount)
//                    .sum());
//        }
//
//        // Count ceiling
//        if (detail.getCeilingList() != null) {
//            dto.setCeilingTotal(detail.getCeilingList().stream()
//                    .mapToInt(Ceiling::getAmount)
//                    .sum());
//        }
//
//        // Count door
//        if (detail.getDoorList() != null) {
//            dto.setDoorTotal(detail.getDoorList().stream()
//                    .mapToInt(Door::getAmount)
//                    .sum());
//        }
//
//        // Count inside walls
//        if (detail.getInsideWallList() != null) {
//            dto.setInsideWallTotal(detail.getInsideWallList().stream()
//                    .mapToInt(InsideWall::getAmount)
//                    .sum());
//        }
//
//        // Count windows
//        if (detail.getWindowList() != null) {
//            dto.setWindowTotal(detail.getWindowList().stream()
//                    .mapToInt(Window::getAmount)
//                    .sum());
//        }
//
//        // Count door price items
//        List<Calculate> doorPrices = estimateService.findByStructureIdAndType(detail.getStructure().getId());
//        dto.setDoorPriceCount(doorPrices.size());
//
//        // Include structure detail
//        dto.setStructureDetail(detail);

        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/inside-wall/{id}")
    public ResponseEntity<Void> deleteInsideWall(@PathVariable Long id) {
        estimateDetailService.deleteInsideWall(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/ceiling/{id}")
    public ResponseEntity<Void> deleteCeiling(@PathVariable Long id) {
        estimateDetailService.deleteCeiling(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/window/{id}")
    public ResponseEntity<Void> deleteWindow(@PathVariable Long id) {
        estimateDetailService.deleteWindow(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/door/{id}")
    public ResponseEntity<Void> deleteDoor(@PathVariable Long id) {
        estimateDetailService.deleteDoor(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/canopy/{id}")
    public ResponseEntity<Void> deleteCanopy(@PathVariable Long id) {
        estimateDetailService.deleteCanopy(id);
        return ResponseEntity.noContent().build();
    }


}



