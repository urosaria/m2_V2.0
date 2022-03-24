package jungjin.board.repository;

import java.util.List;
import javax.transaction.Transactional;
import jungjin.board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findByBoardMasterIdAndStatus(Pageable paramPageable, int paramInt, String paramString);

    List<Board> findByBoardMasterIdAndUserNum(int paramInt, Long paramLong);

    @Transactional
    @Modifying
    @Query("update Board set read_count=read_count+1 where id = :id")
    void updateBoardReadcount(@Param("id") Long paramLong);
}
