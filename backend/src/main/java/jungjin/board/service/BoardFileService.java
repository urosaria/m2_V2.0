package jungjin.board.service;

import jakarta.transaction.Transactional;
import jungjin.board.domain.Board;
import jungjin.board.domain.BoardFile;
import jungjin.board.repository.BoardFileRepository;
import jungjin.config.UploadConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class BoardFileService {

    private final BoardFileRepository boardFileRepository;
    private final UploadConfig uploadConfig;
    private static final String FILE_STORAGE_PATH = "/board/";

    private String storeFile(MultipartFile file) throws IOException {
        String uploadPath = uploadConfig.getUploadDir() + FILE_STORAGE_PATH;
        String originalFilename = file.getOriginalFilename();
        String storedFilename = UUID.randomUUID() + "_" + originalFilename;

        File dir = new File(uploadPath);
        if (!dir.exists()) dir.mkdirs();

        File dest = new File(uploadPath + storedFilename);
        file.transferTo(dest);

        return "/board/" + storedFilename;  // Return the file path
    }

    public void saveOrUpdateFiles(Board board, List<MultipartFile> files, String deleteFiles) {
        Set<BoardFile> currentFiles = new HashSet<>(board.getFiles());
        // Check duplicates based on original file names rather than stored path
        Set<String> existingFileNames = currentFiles.stream()
                .map(BoardFile::getOriName)
                .collect(Collectors.toSet());

        if(files!=null) {
            files.forEach(file -> {
                String filePath = null;
                try {
                    // Avoid uploading duplicate files with the same original name
                    if (!existingFileNames.contains(file.getOriginalFilename())) {
                        filePath = storeFile(file);  // Store the file and get the file path

                        // Create new BoardFile entity and save it
                        BoardFile newFile = new BoardFile();
                        newFile.setOriName(file.getOriginalFilename());
                        newFile.setPath(filePath);
                        newFile.setExt(file.getContentType());
                        newFile.setSize(file.getSize());
                        newFile.setBoard(board);  // Associate the file with the board

                        boardFileRepository.save(newFile);
                        board.getFiles().add(newFile);  // Add to the board's file list
                        existingFileNames.add(file.getOriginalFilename());
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Failed to store file: " + file.getOriginalFilename(), e);
                }
            });
        }

        if (deleteFiles != null && !deleteFiles.isEmpty()) {
            List<Long> fileIdsToDelete = Arrays.stream(deleteFiles.split(","))
                    .map(Long::parseLong)  // Convert string IDs to Long
                    .collect(Collectors.toList());
            removeDeletedFiles(currentFiles, fileIdsToDelete, board);
        }
    }

    private void removeDeletedFiles(Set<BoardFile> currentFiles, List<Long> fileIdsToDelete, Board board) {
        currentFiles.forEach(file -> {
            if (fileIdsToDelete.contains(file.getId())) {  // Match based on file ID
                deleteFile(file, board);  // Delete the file from DB and file system
            }
        });
    }

    private void deleteFile(BoardFile file, Board board) {
        File fileToDelete = new File(uploadConfig.getUploadDir() + file.getPath());

        if (fileToDelete.exists() && fileToDelete.delete()) {
            try {
                board.getFiles().remove(file);
                boardFileRepository.delete(file);  // Ensure that the file is deleted from the DB as well
            } catch (Exception e) {
                throw new RuntimeException("Failed to delete file from database: " + file.getPath(), e);
            }
        } else {
            throw new RuntimeException("Failed to delete file from the file system: " + file.getPath());
        }
    }
}