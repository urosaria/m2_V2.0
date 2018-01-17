package jungjin.board.repository;

import jungjin.board.domain.Board;
import jungjin.board.domain.BoardReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardReplyRepository extends JpaRepository<BoardReply, Long> {
    List<BoardReply> findByBoardId(Long board_id);
}
