package jungjin.estimate.repository;

import jungjin.estimate.domain.Downpipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstimateDownpipeRepository extends JpaRepository<Downpipe, Long> {}
