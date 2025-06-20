package jungjin.board.repository;

import java.util.List;
import jungjin.board.domain.BoardReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardReplyRepository extends JpaRepository<BoardReply, Long> {
    List<BoardReply> findByBoardId(Long paramLong);
}
