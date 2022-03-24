package jungjin.estimate.repository;

import java.util.List;
import javax.transaction.Transactional;
import jungjin.estimate.domain.Door;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EstimateDoorRepository extends JpaRepository<Door, Long> {
    @Transactional
    @Modifying
    @Query("delete from Door where structureDetail.id = :structureDetailId")
    void deleteByStructureDetailId(@Param("structureDetailId") Long paramLong);

    List<Door> findByStructureDetailId(Long paramLong);
}
