package jungjin.estimate.repository;

import java.util.List;
import jakarta.transaction.Transactional;
import jungjin.estimate.domain.Ceiling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EstimateCeilingRepository extends JpaRepository<Ceiling, Long> {
    @Transactional
    @Modifying
    @Query("delete from Ceiling where structureDetail.id = :structureDetailId")
    void deleteByStructureDetailId(@Param("structureDetailId") Long paramLong);

    List<Ceiling> findByStructureDetailId(Long paramLong);
}
