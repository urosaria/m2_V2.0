package jungjin.picture.service;

import jakarta.persistence.EntityNotFoundException;
import jungjin.admin.dto.StatDTO;
import jungjin.config.UploadConfig;
import jungjin.picture.domain.Picture;
import jungjin.picture.domain.PictureAdminFile;
import jungjin.picture.domain.PictureFile;
import jungjin.picture.dto.PictureRequestDTO;
import jungjin.picture.dto.PictureResponseDTO;
import jungjin.picture.repository.PictureAdminFileRepository;
import jungjin.picture.repository.PictureFileRepository;
import jungjin.picture.repository.PictureRepository;

import jungjin.user.domain.User;
import jungjin.user.service.UserV2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PictureService {

    private final PictureRepository pictureRepository;
    private final PictureFileRepository pictureFileRepository;
    private final PictureAdminFileRepository pictureAdminFileRepository;
    private final UserV2Service userService;
    private final UploadConfig uploadConfig;
    private static final String FILE_STORAGE_PATH = "/picture/";

    public Page<PictureResponseDTO> getPictures(Pageable pageable) {
        return pictureRepository.findAll(pageable)
                .map(PictureResponseDTO::fromPicture);
    }

    public Page<PictureResponseDTO> listPicture(int page, int size) {
        int safePage = Math.max(0, page - 1);
        PageRequest request = PageRequest.of(safePage, size, Sort.by(Sort.Direction.DESC, "createDate"));

        Page<Picture> pictures = pictureRepository.findAll(request);

        return pictures.map(PictureResponseDTO::fromPicture);
    }

    @Transactional(readOnly = true)
    public Page<PictureResponseDTO> listPictureById(int page, int size, Long userNum) {
        int safePage = Math.max(0, page - 1);
        Pageable request = PageRequest.of(safePage, size, Sort.by(Sort.Direction.DESC, "createDate"));

        Page<Picture> pictures = pictureRepository.findByUserNum(userNum, request);

        return pictures.map(PictureResponseDTO::fromPicture);
    }

    public PictureResponseDTO getById(Long id) {
        Picture picture = pictureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Picture not found with id: " + id));

        return PictureResponseDTO.fromPicture(picture);
    }
    public PictureResponseDTO register(PictureRequestDTO pictureRequest, MultipartFile[] files) throws IOException {
        // TODO: Replace 2L with token-derived user ID
        User user = userService.getUserByUserNumReturnUser(2L);

        // Convert DTO to Entity with user
        Picture picture = pictureRequest.toEntity(user);

        // Save Picture
        Picture savedPicture = pictureRepository.save(picture);

        // Save attached files
        if (files != null) {
            String filePath = null;
            for (MultipartFile file : files) {
                PictureFile pictureFile = new PictureFile();
                pictureFile.setPicture(savedPicture);
                pictureFile.setOriName(file.getOriginalFilename());

                filePath = storeFile(file);  // Store the file and get the file path
                pictureFile.setPath(filePath);

                // Optional: set file path, UUID, etc.
                pictureFileRepository.save(pictureFile);
            }
        }
        return PictureResponseDTO.fromPicture(savedPicture);
    }

    public void delete(Long id) {
        Picture picture = pictureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Picture not found with id: " + id));
        pictureRepository.delete(picture);
    }

    public void deleteFile(Long fileId) {
        PictureFile file = pictureFileRepository.findById(fileId)
                .orElseThrow(() -> new EntityNotFoundException("File not found with id: " + fileId));

        // Delete physical file
        String filePath = uploadConfig.getUploadDir() + file.getPath();
        File physicalFile = new File(filePath);
        if (physicalFile.exists()) {
            physicalFile.delete();
        }

        // Delete database record
        pictureFileRepository.delete(file);
    }

    public PictureResponseDTO update(Long id, PictureRequestDTO pictureRequest, MultipartFile[] files) throws IOException {
        Picture picture = pictureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Picture not found with id: " + id));

        picture.setName(pictureRequest.getName());
        picture.setEtc(pictureRequest.getEtc());
        picture.setStatus(pictureRequest.getStatus());

        // Update picture
        Picture updatedPicture = pictureRepository.save(picture);

        // Update or save new files
        if (files != null) {
            String filePath = null;
            for (MultipartFile file : files) {
                PictureFile pictureFile = new PictureFile();
                pictureFile.setPicture(updatedPicture);
                pictureFile.setOriName(file.getOriginalFilename());
                filePath = storeFile(file);
                pictureFile.setPath(filePath);
                pictureFileRepository.save(pictureFile);
            }
        }

        return PictureResponseDTO.fromPicture(updatedPicture);
    }

    public PictureResponseDTO updateStatus(Long id, String status) {
        Picture picture = pictureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Picture not found with id: " + id));

        picture.setStatus(status);
        Picture updatedPicture = pictureRepository.save(picture);

        return PictureResponseDTO.fromPicture(updatedPicture);
    }

    public StatDTO getStats() {
        long total = pictureRepository.count();
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
        long todayCount = pictureRepository.countByCreateDateBetween(startOfDay, endOfDay);
        return StatDTO.builder().total(total).today(todayCount).build();
    }

    public PictureResponseDTO adminFileUpload(Long id, MultipartFile[] files) throws IOException {
        Picture picture = pictureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Picture not found with id: " + id));
        picture.setStatus("S4");
        pictureRepository.save(picture);
        if (files != null) {
            for (MultipartFile file : files) {
                PictureAdminFile pictureFile = new PictureAdminFile();
                pictureFile.setPicture(picture);
                pictureFile.setOriName(file.getOriginalFilename());

                String filePath = storeFile(file);
                pictureFile.setPath(filePath);

                pictureAdminFileRepository.save(pictureFile);
            }
        }

        return PictureResponseDTO.fromPicture(picture);
    }

    public Page<Picture> findByStatus(int page, int size) {
        PageRequest request = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createDate"));
        return this.pictureRepository.findByStatus("S4", (Pageable)request);
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

    public Picture deletePicture(Long id) {
        Picture picture = this.pictureRepository.findById(id).orElse(null);
        if (picture == null) {
            throw new EntityNotFoundException("Picture with id " + id + " not found.");
        }

        picture.setStatus("D"); // soft delete
        return this.pictureRepository.save(picture);
    }

    private String storeFile(MultipartFile file) throws IOException {
        String uploadPath = uploadConfig.getUploadDir() + FILE_STORAGE_PATH;
        String originalFilename = file.getOriginalFilename();
        String storedFilename = UUID.randomUUID() + "_" + originalFilename;

        // Ensure directory exists
        File dir = new File(uploadPath);
        if (!dir.exists()) dir.mkdirs();

        // Save the file to disk
        File dest = new File(uploadPath + storedFilename);
        file.transferTo(dest);

        return "/picture/" + storedFilename;  // Return the file path
    }
}
