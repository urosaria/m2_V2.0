package jungjin.estimate.repository;

import jungjin.estimate.domain.StructureDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstimateDetailRepository extends JpaRepository<StructureDetail, Long> {
    StructureDetail findByStructureId(Long paramLong);
}
