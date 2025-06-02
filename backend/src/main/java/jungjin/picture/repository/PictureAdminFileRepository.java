package jungjin.picture.repository;

import jungjin.picture.domain.PictureAdminFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureAdminFileRepository extends JpaRepository<PictureAdminFile, Long> {
}
