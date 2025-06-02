package jungjin.picture.repository;

import jungjin.picture.domain.Picture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
    Page<Picture> findByUserNum(Long userNum, Pageable pageable);

    long count();

    long countByCreateDateBetween(LocalDateTime start, LocalDateTime end);
}
