package jungjin.estimate.repository;

import jungjin.estimate.domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstimatePriceRepository extends JpaRepository<Price, Integer> {
    Price findByGubunAndSubGubunAndTypeAndSubType(String paramString1, String paramString2, String paramString3, String paramString4);
}
