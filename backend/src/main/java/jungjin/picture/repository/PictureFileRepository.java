package jungjin.picture.repository;

import jungjin.picture.domain.PictureFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureFileRepository extends JpaRepository<PictureFile, Long> {
}
