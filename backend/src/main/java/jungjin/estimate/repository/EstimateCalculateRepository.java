package jungjin.estimate.repository;

import java.util.List;
import jakarta.transaction.Transactional;
import jungjin.estimate.domain.Calculate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EstimateCalculateRepository extends JpaRepository<Calculate, Long> {

    List<Calculate> findByStructureIdOrderBySortAsc(Long structureId);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Calculate e where e.structure.id = :structureId")
    void deleteByStructureId(@Param("structureId") Long structureId);

    List<Calculate> findByStructureIdAndType(Long structureId, String type);
}