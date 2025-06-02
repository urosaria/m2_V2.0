package jungjin.estimate.repository;

import jungjin.estimate.domain.Window;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstimateWindowRepository extends JpaRepository<Window, Long> {
}
