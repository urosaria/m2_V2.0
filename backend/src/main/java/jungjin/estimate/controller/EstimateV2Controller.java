package jungjin.estimate.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jungjin.common.exception.NotFoundException;
import jungjin.config.UploadConfig;
import jungjin.estimate.dto.EstimateRequestDTO;
import jungjin.estimate.dto.EstimateResponseDTO;
import jungjin.estimate.service.EstimateExcelServiceV2;
import jungjin.estimate.service.EstimateServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.springframework.http.HttpHeaders;

@RestController
@RequestMapping("/api/estimates")
@RequiredArgsConstructor
public class EstimateV2Controller {

    private final EstimateServiceV2 estimateService;
    private final UploadConfig uploadConfig;
    private final EstimateExcelServiceV2 excelService;

    @GetMapping
    public ResponseEntity<?> getUserEstimates(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Page<EstimateResponseDTO> estimates = estimateService.getUserEstimates(page, size);
            return ResponseEntity.ok(estimates);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve estimates: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createEstimate(@RequestBody @Valid EstimateRequestDTO request) {
        try {
            //UserCustom principal = (UserCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            //User user = userService.showUser(2L);

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

    @GetMapping("/{id}/excelDownload")
    public ResponseEntity<Resource> downloadExcel(
            @PathVariable Long id,
            HttpServletRequest request
    ) throws IOException {

        excelService.excel(id);

        String fileName = "estimate" + id + ".xlsx";
        String filePath = uploadConfig.getUploadDir() + "/estimate/" + fileName;
        File file = new File(filePath);

        if (!file.exists()) {
            throw new NotFoundException("Failed to download excel file:" + id);
        }

        EstimateResponseDTO structure = estimateService.getEstimateById(id);
        String displayName = structure.getPlaceName() + ".xlsx";
        String contentType = Optional.ofNullable(
                request.getServletContext().getMimeType(file.getAbsolutePath())
        ).orElse("application/octet-stream");

        String encodedFileName = encodeFileName(request.getHeader("User-Agent"), displayName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                .contentLength(file.length())
                .body(resource);
    }

    private String encodeFileName(String userAgent, String fileName) {
        if (userAgent != null && (userAgent.contains("MSIE") || userAgent.contains("Trident") || userAgent.contains("Edge"))) {
            return URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", " ");
        } else {
            return new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        }
    }
}
