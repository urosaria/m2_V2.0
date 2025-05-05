package jungjin.picture.service;

import jakarta.persistence.EntityNotFoundException;
import jungjin.picture.domain.Picture;
import jungjin.picture.domain.PictureAdminFile;
import jungjin.picture.domain.PictureFile;
import jungjin.picture.repository.PictureAdminFileRepository;
import jungjin.picture.repository.PictureFileRepository;
import jungjin.picture.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PictureService {

    PictureRepository pictureRepository;
    PictureFileRepository pictureFileRepository;
    PictureAdminFileRepository pictureAdminFileRepository;

    public Page<Picture> listPicture(int page, int size) {
        PageRequest request = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createDate"));
        return this.pictureRepository.findAll((Pageable)request);
    }

    public Page<Picture> findByStatus(int page, int size) {
        PageRequest request = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createDate"));
        return this.pictureRepository.findByStatus("S4", (Pageable)request);
    }

    public Page<Picture> listPictureById(int page, int size, Long userNum) {
        PageRequest request = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createDate"));
        return this.pictureRepository.findByUserNumAndStatusNot(userNum, "D", (Pageable)request);
    }

    public Picture savePicture(Picture insertPicture) {
        try {
            return this.pictureRepository.save(insertPicture);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save Picture", e);
        }
    }

    public Picture showPicture(Long id) {
        return this.pictureRepository.findById(id).orElse(null);
    }

    public PictureFile savePictureFile(PictureFile insertPictureFile) {
        try {
            return this.pictureFileRepository.save(insertPictureFile);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save PictureFile", e);
        }
    }

    public PictureFile fileDetailService(Long id) {
        return this.pictureFileRepository.findById(id).orElse(null);
    }

    public PictureAdminFile adminFileDetailService(Long id) {
        return this.pictureAdminFileRepository.findById(id).orElse(null);
    }

    public void updatePictureStatus(Long id, String status) {
        this.pictureRepository.updatePictureStatus(id, status);
    }

    public void deletePictureAdminFile(Long id) {
        this.pictureAdminFileRepository.deletePictureAdminFile(id);
    }

    public void depetePictureFile(Long[] ids) {
        this.pictureFileRepository.depetePictureFile(ids);
    }

    public PictureAdminFile savePictureAdminFile(PictureAdminFile pictureAdminFile) {
        try {
            return this.pictureAdminFileRepository.save(pictureAdminFile);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save PictureAdminFile", e);
        }
    }

    @Transactional
    public Picture deletePicture(Long id) {
        Picture picture = this.pictureRepository.findById(id).orElse(null);
        if (picture == null) {
            throw new EntityNotFoundException("Picture with id " + id + " not found.");
        }

        picture.setStatus("D"); // soft delete
        return this.pictureRepository.save(picture);
    }
}
