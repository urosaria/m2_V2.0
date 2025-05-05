package jungjin.estimate.repository;

import java.util.List;
import jakarta.transaction.Transactional;
import jungjin.estimate.domain.Window;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EstimateWindowRepository extends JpaRepository<Window, Long> {
    @Transactional
    @Modifying
    @Query("delete from Window where structureDetail.id = :structureDetailId")
    void deleteByStructureDetailId(@Param("structureDetailId") Long paramLong);

    List<Window> findByStructureDetailId(Long paramLong);
}
