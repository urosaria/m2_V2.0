package jungjin.estimate.service;

import jungjin.estimate.domain.Price;
import jungjin.estimate.dto.EstimatePriceRequestDTO;
import jungjin.estimate.repository.EstimatePriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EstimatePriceService {

    private final EstimatePriceRepository estimatePriceRepository;

    public Price showPrice(String gubun, String subGubun, String type, String subType) {
        return estimatePriceRepository.findByGubunAndSubGubunAndTypeAndSubType(gubun, subGubun, type, subType);
    }

    public List<Price> getAllPrices() {
        return estimatePriceRepository.findAll();
    }

    public List<Price> searchPrices(String gubun, String subGubun, String type, String subType) {
        return estimatePriceRepository.findAll().stream()
                .filter(p -> gubun == null || p.getGubun().equalsIgnoreCase(gubun))
                .filter(p -> subGubun == null || p.getSubGubun().equalsIgnoreCase(subGubun))
                .filter(p -> type == null || p.getType().equalsIgnoreCase(type))
                .filter(p -> subType == null || p.getSubType().equalsIgnoreCase(subType))
                .collect(Collectors.toList());
    }

    public Price getPriceById(Integer id) {
        return estimatePriceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Price not found with id: " + id));
    }

    @Transactional
    public Price createPrice(EstimatePriceRequestDTO requestDTO) {
        Price price = requestDTO.toEntity();
        return estimatePriceRepository.save(price);
    }

    @Transactional
    public Price updatePrice(Integer id, EstimatePriceRequestDTO requestDTO) {
        Price existingPrice = getPriceById(id);
        requestDTO.updateEntity(existingPrice);
        
        return estimatePriceRepository.save(existingPrice);
    }

    @Transactional
    public void deletePrice(Integer id) {
        if (!estimatePriceRepository.existsById(id)) {
            throw new IllegalArgumentException("Price not found with id: " + id);
        }
        estimatePriceRepository.deleteById(id);
    }


}
