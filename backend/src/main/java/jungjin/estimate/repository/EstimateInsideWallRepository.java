package jungjin.estimate.repository;

import java.util.List;
import jakarta.transaction.Transactional;
import jungjin.estimate.domain.InsideWall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EstimateInsideWallRepository extends JpaRepository<InsideWall, Long> {
    @Transactional
    @Modifying
    @Query("delete from InsideWall where structureDetail.id = :structureDetailId")
    void deleteByStructureDetailId(@Param("structureDetailId") Long paramLong);

    List<InsideWall> findByStructureDetailId(Long paramLong);
}
