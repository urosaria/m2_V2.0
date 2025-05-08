package jungjin.estimate.controller;

import jakarta.persistence.EntityNotFoundException;
import jungjin.common.exception.NotFoundException;
import jungjin.estimate.dto.EstimateRequestDTO;
import jungjin.estimate.dto.EstimateResponseDTO;
import jungjin.estimate.service.EstimateServiceV2;
import jungjin.user.domain.User;
import jungjin.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estimates")
@RequiredArgsConstructor
public class EstimateV2Controller {

    private final EstimateServiceV2 estimateService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createEstimate(@RequestBody EstimateRequestDTO request) {
        try {
            //UserCustom principal = (UserCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.showUser(2L);

            EstimateResponseDTO result = estimateService.createEstimate(request);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save estimate: " + e.getMessage());
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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEstimate(@PathVariable Long id, @RequestBody EstimateRequestDTO request) {
        try {
            EstimateResponseDTO updated = estimateService.updateEstimate(id, request);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Estimate not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update estimate: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEstimate(@PathVariable Long id) {
        try {
            estimateService.deleteEstimate(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Estimate not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete estimate: " + e.getMessage());
        }
    }
}
