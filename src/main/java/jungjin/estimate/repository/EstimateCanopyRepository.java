package jungjin.estimate.repository;

import java.util.List;
import javax.transaction.Transactional;
import jungjin.estimate.domain.Canopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EstimateCanopyRepository extends JpaRepository<Canopy, Long> {
    @Transactional
    @Modifying
    @Query("delete from Canopy where structureDetail.id = :structureDetailId")
    void deleteByStructureDetailId(@Param("structureDetailId") Long paramLong);

    List<Canopy> findByStructureDetailId(Long paramLong);
}
