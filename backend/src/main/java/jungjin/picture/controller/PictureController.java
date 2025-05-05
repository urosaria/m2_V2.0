package jungjin.picture.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jungjin.M2Application;
import jungjin.picture.domain.Picture;
import jungjin.picture.domain.PictureAdminFile;
import jungjin.picture.domain.PictureFile;
import jungjin.picture.service.PictureService;
import jungjin.user.service.UserCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api/pictures")
@RequiredArgsConstructor
public class PictureController {

    private final PictureService pictureService;

    @GetMapping
    public ResponseEntity<Page<Picture>> list(@RequestParam(defaultValue = "1") int page) {
        Long userNum = ((UserCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getNum();
        Page<Picture> pictureList = pictureService.listPictureById(page, 4, userNum);
        return ResponseEntity.ok(pictureList);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Picture>> listAll(@RequestParam(defaultValue = "1") int page) {
        Page<Picture> pictureAllList = pictureService.findByStatus(page, 6);
        return ResponseEntity.ok(pictureAllList);
    }

    @PostMapping
    public ResponseEntity<Picture> register(@RequestPart("picture") Picture picture,
                                            @RequestPart(value = "files", required = false) MultipartFile[] files,
                                            @RequestParam(value = "fileDeleteArray", required = false) Long[] fileDeleteArray) {
        picture.setUser(picture.getUser());
        picture.setCreateDate(LocalDateTime.now());
        Picture result = pictureService.savePicture(picture);

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                try {
                    MultipartFile file = files[i];
                    if (!file.isEmpty()) {
                        String timestamp = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
                        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                        String fileName = timestamp + "_" + result.getId() + "_" + (i + 1) + ext;
                        File targetFile = new File(M2Application.UPLOAD_DIR + "/picture/" + fileName);
                        targetFile.getParentFile().mkdirs();
                        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(targetFile))) {
                            bos.write(file.getBytes());
                        }

                        PictureFile pictureFile = new PictureFile();
                        pictureFile.setName(fileName);
                        pictureFile.setExt(ext);
                        pictureFile.setOriName(file.getOriginalFilename());
                        pictureFile.setPicture(result);
                        pictureFile.setCreateDate(result.getCreateDate());
                        pictureService.savePictureFile(pictureFile);
                    }
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            }
        }

        if (fileDeleteArray != null) {
            pictureService.depetePictureFile(fileDeleteArray);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Picture> show(@PathVariable Long id) {
        Picture detail = pictureService.showPicture(id);
        return ResponseEntity.ok(detail);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> remove(@PathVariable Long id) {
        Picture picture = pictureService.deletePicture(id);
        return picture != null ? ResponseEntity.ok("success") : ResponseEntity.status(HttpStatus.NOT_FOUND).body("fail");
    }

    @GetMapping("/download/{fileId}")
    public void downloadFile(@PathVariable Long fileId,
                             @RequestParam(value = "type", defaultValue = "user") String type,
                             HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        String fileUrl = M2Application.UPLOAD_DIR + "/picture/";
        String fileName;
        String oriFileName;

        if ("admin".equals(type)) {
            PictureAdminFile fileVO = pictureService.adminFileDetailService(fileId);
            fileName = fileVO.getName();
            oriFileName = fileVO.getOriName();
            fileUrl += fileVO.getPath();
        } else {
            PictureFile fileVO = pictureService.fileDetailService(fileId);
            fileName = fileVO.getName();
            oriFileName = fileVO.getOriName();
        }

        File file = new File(fileUrl, fileName);
        if (!file.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("파일을 찾을 수 없습니다.");
            return;
        }

        response.setContentType("application/octet-stream");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(oriFileName, StandardCharsets.UTF_8).replaceAll("\\+", " ") + "\"");
        response.setContentLengthLong(file.length());

        try (InputStream in = new FileInputStream(file); OutputStream out = response.getOutputStream()) {
            FileCopyUtils.copy(in, out);
        }
    }

    @GetMapping("/download-zip/{id}")
    public void downloadZip(@PathVariable Long id, @RequestParam(value = "sName", defaultValue = "all") String sName, HttpServletResponse response) throws IOException {
        String dirPath = M2Application.UPLOAD_DIR + "/picture/admin/" + id;
        File zipFile = new File(dirPath + "/all.zip");
        if (zipFile.exists()) zipFile.delete();

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            File dir = new File(dirPath);
            for (File file : dir.listFiles()) {
                zos.putNextEntry(new ZipEntry(file.getName()));
                try (FileInputStream fis = new FileInputStream(file)) {
                    FileCopyUtils.copy(fis, zos);
                }
                zos.closeEntry();
            }
        }

        response.setContentType("application/zip");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(sName + ".zip", StandardCharsets.UTF_8) + "\"");
        response.setContentLengthLong(zipFile.length());

        try (InputStream in = new FileInputStream(zipFile); OutputStream out = response.getOutputStream()) {
            FileCopyUtils.copy(in, out);
        }
    }
}