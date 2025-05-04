package jungjin.picture.repository;

import jakarta.transaction.Transactional;
import jungjin.picture.domain.PictureAdminFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PictureAdminFileRepository extends JpaRepository<PictureAdminFile, Long> {
    @Transactional
    @Modifying
    @Query("delete from PictureAdminFile where id=:id")
    void deletePictureAdminFile(@Param("id") Long paramLong);
}
