package jungjin.common.controller;

import jungjin.common.exception.BusinessException;
import jungjin.common.exception.NotFoundException;
import jungjin.config.UploadConfig;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final UploadConfig uploadConfig;

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam("path") String filePath,
                                                 @RequestParam("name") String fileName) throws IOException {
        File file = validateAndResolveFile(filePath);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFileName)
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.length())
                .body(resource);
    }

    private File validateAndResolveFile(String filePath) throws IOException {
        File fullFile = new File(uploadConfig.getUploadDir(), filePath);

        String normalizedFullPath = fullFile.getCanonicalPath();
        String normalizedBasePath = new File(uploadConfig.getUploadDir()).getCanonicalPath();

        if (!normalizedFullPath.startsWith(normalizedBasePath + File.separator)) {
            throw new BusinessException("INVALID_PATH", "Invalid file path");
        }

        if (!fullFile.exists() || !fullFile.isFile()) {
            throw new NotFoundException("File not found: " + filePath);
        }

        return fullFile;
    }

    @GetMapping("/thumbnail")
    public ResponseEntity<Resource> getThumbnail(@RequestParam("path") String filePath) throws IOException {
        File file = validateAndResolveFile(filePath);

        ByteArrayOutputStream thumbStream = new ByteArrayOutputStream();
        Thumbnails.of(file)
                .size(200, 200)
                .outputFormat("jpeg")
                .toOutputStream(thumbStream);

        byte[] thumbBytes = thumbStream.toByteArray();
        Resource resource = new InputStreamResource(new ByteArrayInputStream(thumbBytes));

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .contentLength(thumbBytes.length)
                .body(resource);
    }
}
