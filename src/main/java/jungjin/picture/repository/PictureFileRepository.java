package jungjin.picture.repository;

import javax.transaction.Transactional;
import jungjin.picture.domain.PictureFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PictureFileRepository extends JpaRepository<PictureFile, Long> {
    @Transactional
    @Modifying
    @Query("delete from PictureFile where id IN :ids")
    void depetePictureFile(@Param("ids") Long[] paramArrayOfLong);
}
