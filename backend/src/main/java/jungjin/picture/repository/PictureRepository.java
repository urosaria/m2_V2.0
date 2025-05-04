package jungjin.picture.repository;

import jakarta.transaction.Transactional;
import jungjin.picture.domain.Picture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PictureRepository extends JpaRepository<Picture, Long> {
    Page<Picture> findByStatus(String paramString, Pageable paramPageable);

    Page<Picture> findByUserNumAndStatusNot(Long paramLong, String paramString, Pageable paramPageable);

    @Transactional
    @Modifying
    @Query("update Picture set status=:status where id = :id")
    void updatePictureStatus(@Param("id") Long paramLong, @Param("status") String paramString);
}
