package jungjin.estimate.repository;

import jungjin.estimate.domain.StructureExcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstimateExcelRepository extends JpaRepository<StructureExcel, Long> {
    Optional<StructureExcel> findByStructureId(Long structureId);
}
