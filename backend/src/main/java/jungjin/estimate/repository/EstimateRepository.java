package jungjin.estimate.repository;

import java.time.LocalDateTime;
import jungjin.estimate.domain.Structure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstimateRepository extends JpaRepository<Structure, Long> {
    Page<Structure> findByUserNum(Long userNum, Pageable pageable);

    long count();

    long countByCreateDateBetween(LocalDateTime start, LocalDateTime end);
}
