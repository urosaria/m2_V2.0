package jungjin.picture.controller;

import jakarta.servlet.http.HttpServletRequest;
import jungjin.M2Application;
import jungjin.config.UploadConfig;
import jungjin.picture.domain.Picture;
import jungjin.picture.domain.PictureAdminFile;
import jungjin.picture.service.PictureService;
import jungjin.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/api/admin/picture")
@RequiredArgsConstructor
public class PictureAdminController {

    private final PictureService pictureService;
    private final UploadConfig uploadConfig;

    @GetMapping("/list")
    public ResponseEntity<Page<Picture>> list(@RequestParam(defaultValue = "1") int page, User user) {
        Page<Picture> pictureList = pictureService.listPicture(page, 10);
        return ResponseEntity.ok(pictureList);
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<Picture> show(@PathVariable Long id) {
        Picture detail = pictureService.showPicture(id);
        return ResponseEntity.ok(detail);
    }

    @GetMapping("/register")
    public ResponseEntity<Picture> register(@RequestParam(required = false) Long id) {
        Picture picture = (id != null) ? pictureService.showPicture(id) : new Picture();
        return ResponseEntity.ok(picture);
    }

    @PostMapping("/register")
    public ResponseEntity<Picture> insert(@RequestBody Picture picture) {
        Picture existing = pictureService.showPicture(picture.getId());
        existing.update(picture);
        Picture saved = pictureService.savePicture(existing);
        return ResponseEntity.ok(saved);
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<String> updateStatusQuick(@PathVariable Long id, @RequestParam String process) {
        pictureService.updatePictureStatus(id, process);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/status")
    public ResponseEntity<Picture> updateStatus(@RequestBody Picture picture) {
        Picture existing = pictureService.showPicture(picture.getId());
        existing.updateStatus(picture);
        Picture saved = pictureService.savePicture(existing);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFiles(
            @RequestParam("file") MultipartFile[] files,
            @RequestParam("id") Long id) {

        if (files == null || files.length == 0) {
            return ResponseEntity.badRequest().body("No files uploaded");
        }

        try {
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                if (!file.isEmpty()) {
                    byte[] bytes = file.getBytes();
                    String timestamp = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
                    String baseName = timestamp + "_" + id + "_" + (i + 1);
                    String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                    String fileName = baseName + ext;

                    File dir = new File(uploadConfig + "/picture/admin/" + id);
                    if (!dir.exists()) dir.mkdirs();

                    try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(dir, fileName)))) {
                        bos.write(bytes);
                    }

                    PictureAdminFile fileEntity = new PictureAdminFile();
                    fileEntity.setName(fileName);
                    fileEntity.setExt(ext);
                    fileEntity.setOriName(file.getOriginalFilename());
                    fileEntity.setPicture(pictureService.showPicture(id));
                    fileEntity.setCreateDate(LocalDateTime.now());
                    fileEntity.setPath("/admin/" + id);
                    pictureService.savePictureAdminFile(fileEntity);
                }
            }
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed: " + e.getMessage());
        }
    }

    @DeleteMapping("/file/{id}")
    public ResponseEntity<String> removePictureFile(@PathVariable Long id) {
        pictureService.deletePictureAdminFile(id);
        return ResponseEntity.ok("success");
    }
}