package jungjin.picture.controller;

import jungjin.common.exception.BusinessException;
import jungjin.common.exception.NotFoundException;
import jungjin.config.UploadConfig;
import jungjin.picture.domain.PictureFile;
import jungjin.picture.service.PictureService;
import jungjin.picture.dto.PictureRequestDTO;
import jungjin.picture.dto.PictureResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


@RestController
@RequestMapping("/api/pictures")
@RequiredArgsConstructor
public class PictureController {

    private final PictureService pictureService;
    private final UploadConfig uploadConfig;

    @GetMapping("/all")
    public ResponseEntity<Page<PictureResponseDTO>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PictureResponseDTO> pictureList = pictureService.listPicture(page, size);
        return ResponseEntity.ok(pictureList);
    }

    @GetMapping
    public ResponseEntity<Page<PictureResponseDTO>> listById(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        //TODO: need to update
        //User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<PictureResponseDTO> pictureList = pictureService.listPictureById(page, size, 2L); // no -1 here
        return ResponseEntity.ok(pictureList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PictureResponseDTO> view(@PathVariable Long id) {
        PictureResponseDTO response = pictureService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PictureResponseDTO> register(
            @RequestPart("picture") PictureRequestDTO pictureRequest,
            @RequestPart(value = "files", required = false) MultipartFile[] files) throws IOException {

        PictureResponseDTO savedPicture = pictureService.register(pictureRequest, files);
        return ResponseEntity.ok(savedPicture);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pictureService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PictureResponseDTO> update(
            @PathVariable Long id,
            @RequestPart("picture") PictureRequestDTO pictureRequest,
            @RequestPart(value = "files", required = false) MultipartFile[] files) throws IOException {

        PictureResponseDTO updatedDTO = pictureService.update(id, pictureRequest, files);
        return ResponseEntity.ok(updatedDTO);
    }

    @DeleteMapping("/file/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable("fileId") Long fileId) {
        pictureService.deleteFile(fileId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") Long fileId) throws IOException {
        //            PictureAdminFile fileVO = pictureService.adminFileDetailService(fileId);
        PictureFile pictureFile = pictureService.fileDetailService(fileId);

        if (pictureFile  == null) {
            throw new NotFoundException("File not found with id: " + fileId);
        }

        String filePath = uploadConfig.getUploadDir() + pictureFile.getPath();
        File file = new File(filePath);
        if (!file.exists()) {
            throw new BusinessException("FILE_NOT_FOUND", "Physical file not found: " + filePath);
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + pictureFile.getOriName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.length())
                .body(resource);
    }

    @PostMapping("/admin/upload/{id}")
    public ResponseEntity<PictureResponseDTO> adminFileUpload(
            @PathVariable("id") Long id,
            @RequestPart(value = "files", required = false) MultipartFile[] files) throws IOException {

        PictureResponseDTO savedPicture = pictureService.adminFileUpload(id, files);
        return ResponseEntity.ok(savedPicture);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PictureResponseDTO> updateStatus(
            @PathVariable("id") Long id,
            @RequestBody PictureRequestDTO dto) {

        PictureResponseDTO updatedPicture = pictureService.updateStatus(id, dto.getStatus());
        return ResponseEntity.ok(updatedPicture);
    }
}