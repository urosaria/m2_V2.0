package jungjin.estimate.controller;

import jungjin.estimate.domain.Structure;
import jungjin.estimate.domain.StructureDetail;
import jungjin.estimate.service.EstimateDetailService;
import jungjin.estimate.service.EstimateService;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/admin/estimate")
@RequiredArgsConstructor
public class EstimateAdminController {
    private final EstimateService estimateService;
    private final EstimateDetailService estimateDetailService;

    @GetMapping("/list")
    public ResponseEntity<?> getEstimateList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String status,
            @RequestParam(defaultValue = "") String searchCondition,
            @RequestParam(defaultValue = "") String searchText) {
        try {
            Page<Structure> estimateList;
            if (!status.isEmpty() || !searchCondition.isEmpty()) {
                estimateList = estimateService.listEstimateStatus(searchCondition, searchText, status, page, 10);
            } else {
                estimateList = estimateService.listEstimateAll(page, 10);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("estimateList", estimateList);
            response.put("page", page);
            response.put("status", status);
            response.put("searchCondition", searchCondition);
            response.put("searchText", searchText);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching estimate list: " + e.getMessage());
        }
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<?> getEstimate(@PathVariable("id") Long id) {
        try {
            Structure structure = estimateService.showEstimate(id);
            StructureDetail detail = estimateDetailService.showEstimateDetail(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("structure", structure);
            response.put("structureDetail", detail);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching estimate: " + e.getMessage());
        }
    }
}
