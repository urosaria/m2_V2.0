package jungjin.estimate.controller;

import jungjin.estimate.domain.Price;
import jungjin.estimate.dto.EstimatePriceRequestDTO;
import jungjin.estimate.service.EstimatePriceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/estimate/prices")
@RequiredArgsConstructor
public class EstimatePriceController {

    private final EstimatePriceService estimatePriceService;

    @GetMapping
    public ResponseEntity<?> getPrices(
            @RequestParam(required = false) String gubun,
            @RequestParam(required = false) String subGubun,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String subType) {

        // No parameters → return all prices
        if (gubun == null && subGubun == null && type == null && subType == null) {
            return ResponseEntity.ok(estimatePriceService.getAllPrices());
        }

        // Partial parameters (e.g., gubun only) → use filtering logic
        List<Price> filteredPrices = estimatePriceService.searchPrices(gubun, subGubun, type, subType);
        return ResponseEntity.ok(filteredPrices);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Price> getPriceById(@PathVariable Integer id) {
        return ResponseEntity.ok(estimatePriceService.getPriceById(id));
    }

//    @GetMapping("/search")
//    public ResponseEntity<Price> searchPrice(
//            @RequestParam String gubun,
//            @RequestParam String subGubun,
//            @RequestParam String type,
//            @RequestParam String subType) {
//        return ResponseEntity.ok(estimatePriceService.showPrice(gubun, subGubun, type, subType));
//    }

    @PostMapping
    public ResponseEntity<Price> createPrice(@Valid @RequestBody EstimatePriceRequestDTO requestDTO) {
        Price createdPrice = estimatePriceService.createPrice(requestDTO);
        return ResponseEntity
                .created(URI.create("/api/estimate/prices/" + createdPrice.getId()))
                .body(createdPrice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Price> updatePrice(
            @PathVariable Integer id,
            @Valid @RequestBody EstimatePriceRequestDTO requestDTO) {
        return ResponseEntity.ok(estimatePriceService.updatePrice(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrice(@PathVariable Integer id) {
        estimatePriceService.deletePrice(id);
        return ResponseEntity.noContent().build();
    }
}
