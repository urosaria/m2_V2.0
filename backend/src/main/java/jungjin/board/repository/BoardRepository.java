package jungjin.board.repository;

import java.util.List;
import jakarta.transaction.Transactional;
import jungjin.board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findByBoardMasterIdAndStatus(Pageable pageable, Long boardMasterId, String status);

    List<Board> findByBoardMasterIdAndUserNum(int paramInt, Long paramLong);

    @Transactional
    @Modifying
    @Query("update Board set readcount=readcount+1 where id = :id")
    void updateBoardReadcount(@Param("id") Long paramLong);
}
