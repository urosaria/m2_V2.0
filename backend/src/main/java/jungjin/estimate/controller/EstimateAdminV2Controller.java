package jungjin.estimate.controller;

import jakarta.persistence.EntityNotFoundException;
import jungjin.estimate.dto.EstimateResponseDTO;
import jungjin.estimate.service.EstimateServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estimates/admin")
@RequiredArgsConstructor
public class EstimateAdminV2Controller {

    private final EstimateServiceV2 estimateService;

    @GetMapping
    public ResponseEntity<?> getEstimates(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Page<EstimateResponseDTO> estimates = estimateService.getAllEstimates(page, size);
            return ResponseEntity.ok(estimates);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve estimates: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEstimate(@PathVariable Long id) {
        try {
            EstimateResponseDTO estimate = estimateService.getEstimateById(id);
            return ResponseEntity.ok(estimate);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Estimate not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve estimate: " + e.getMessage());
        }
    }
}
