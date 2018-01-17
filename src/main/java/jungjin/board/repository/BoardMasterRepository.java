package jungjin.board.repository;

import jungjin.board.domain.BoardMaster;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardMasterRepository extends JpaRepository<BoardMaster, Integer> {
}
