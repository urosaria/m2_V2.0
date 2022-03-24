package jungjin.picture.service;

import jungjin.picture.domain.Picture;
import jungjin.picture.domain.PictureAdminFile;
import jungjin.picture.domain.PictureFile;
import jungjin.picture.repository.PictureAdminFileRepository;
import jungjin.picture.repository.PictureFileRepository;
import jungjin.picture.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PictureService {
    @Autowired
    PictureRepository pictureRepository;

    @Autowired
    PictureFileRepository pictureFileRepository;

    @Autowired
    PictureAdminFileRepository pictureAdminFileRepository;

    public Page<Picture> listPicture(int page, int size) {
        PageRequest request = new PageRequest(page - 1, size, Sort.Direction.DESC, new String[] { "createDate" });
        return this.pictureRepository.findAll((Pageable)request);
    }

    public Page<Picture> findByStatus(int page, int size) {
        PageRequest request = new PageRequest(page - 1, size, Sort.Direction.DESC, new String[] { "createDate" });
        return this.pictureRepository.findByStatus("S4", (Pageable)request);
    }

    public Page<Picture> listPictureById(int page, int size, Long userNum) {
        PageRequest request = new PageRequest(page - 1, size, Sort.Direction.DESC, new String[] { "createDate" });
        return this.pictureRepository.findByUserNumAndStatusNot(userNum, "D", (Pageable)request);
    }

    public Picture savePicture(Picture insertPicture) {
        return (Picture)this.pictureRepository.save(insertPicture);
    }

    public Picture showPicture(Long id) {
        return (Picture)this.pictureRepository.findOne(id);
    }

    public PictureFile savePictureFile(PictureFile insertPictureFile) {
        return (PictureFile)this.pictureFileRepository.save(insertPictureFile);
    }

    public PictureFile fileDetailService(Long id) {
        return (PictureFile)this.pictureFileRepository.findOne(id);
    }

    public PictureAdminFile adminFileDetailService(Long id) {
        return (PictureAdminFile)this.pictureAdminFileRepository.findOne(id);
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
        return (PictureAdminFile)this.pictureAdminFileRepository.save(pictureAdminFile);
    }

    @Transactional
    public Picture deletePicture(Long id) {
        Picture picture = (Picture)this.pictureRepository.findOne(id);
        picture.setStatus("D");
        picture = (Picture)this.pictureRepository.save(picture);
        return picture;
    }
}
