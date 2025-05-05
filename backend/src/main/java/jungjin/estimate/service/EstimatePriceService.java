package jungjin.estimate.service;

import jungjin.estimate.domain.Price;
import jungjin.estimate.repository.EstimatePriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EstimatePriceService {

    EstimatePriceRepository estimatePriceRepository;

    public Price showPrice(String gubun, String subGubun, String type, String subType) {
        return this.estimatePriceRepository.findByGubunAndSubGubunAndTypeAndSubType(gubun, subGubun, type, subType);
    }

}
