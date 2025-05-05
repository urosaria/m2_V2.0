package jungjin.board.repository;

import jungjin.board.domain.BoardMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardMasterRepository extends JpaRepository<BoardMaster, Long> {}
