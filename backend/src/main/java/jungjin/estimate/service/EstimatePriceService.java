package jungjin.estimate.service;

import jungjin.estimate.domain.Price;
import jungjin.estimate.repository.EstimatePriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstimatePriceService {
    @Autowired
    EstimatePriceRepository estimatePriceRepository;

    public Price showPrice(String gubun, String subGubun, String type, String subType) {
        return this.estimatePriceRepository.findByGubunAndSubGubunAndTypeAndSubType(gubun, subGubun, type, subType);
    }
}
