package jungjin.board.repository;

import jakarta.transaction.Transactional;
import jungjin.board.domain.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {
    @Transactional
    @Modifying
    @Query("delete from BoardFile where id IN :ids")
    void deleteBoardFile(@Param("ids") Long[] paramArrayOfLong);
}
