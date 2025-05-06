package jungjin.estimate.controller;

import jungjin.estimate.dto.EstimateRequestDTO;
import jungjin.estimate.dto.EstimateResponseDTO;
import jungjin.estimate.service.EstimateServiceV2;
import jungjin.user.domain.User;
import jungjin.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estimates")
@RequiredArgsConstructor
public class EstimateV2Controller {

    private final EstimateServiceV2 estimateService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createEstimate2(@RequestBody EstimateRequestDTO request) {
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
}
