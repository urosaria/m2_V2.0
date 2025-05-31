package jungjin.board.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jungjin.board.domain.BoardFile;
import jungjin.board.domain.BoardMaster;
import jungjin.board.dto.BoardRequestDTO;
import jungjin.board.dto.BoardResponseDTO;
import jungjin.board.service.BoardMasterService;
import jungjin.board.service.BoardService;
import jungjin.common.exception.NotFoundException;
import jungjin.common.exception.BusinessException;
import jungjin.config.UploadConfig;
import jungjin.user.domain.User;
import jungjin.user.service.UserV2Service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final BoardMasterService boardMasterService;
    private final UserV2Service userV2Service;
    private final UploadConfig uploadConfig;

    @GetMapping("/list/{boardMasterId}")
    public ResponseEntity<?> getBoardList(
            @PathVariable("boardMasterId") int boardMasterId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<BoardResponseDTO> boardList = boardService.listBoard(page, size, boardMasterId);
        return ResponseEntity.ok(boardList);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDTO> getBoard(@PathVariable("boardId") Long boardId) {
        return ResponseEntity.ok(boardService.getBoard(boardId));
    }

//    @PostMapping(value = "/{boardMasterId}", consumes = {"multipart/form-data"})
//    public ResponseEntity<BoardResponseDTO> createBoard(
//            @PathVariable("boardMasterId") Long boardMasterId,
//            @RequestPart(value = "boardRequest") @Valid BoardRequestDTO boardRequest,
//            @RequestPart(value = "files", required = false) List<MultipartFile> files) {
//        //TODO: need to change to retrieve logging user
//        User user = userV2Service.getUserByUserNumReturnUser(2L);
//        BoardMaster boardMaster = boardMasterService.getBoardMasterEntity(boardMasterId);
//        BoardResponseDTO result = boardService.saveBoard(boardRequest, boardMaster, user, files);
//        return ResponseEntity.ok(result);
//    }

    @PostMapping(value = "/{boardMasterId}", consumes = {"multipart/form-data"})
    public ResponseEntity<BoardResponseDTO> createBoard(
            @PathVariable("boardMasterId") Long boardMasterId,
            @RequestParam("boardRequest") String boardRequestJson,
            @RequestPart(value = "files", required = false) List<MultipartFile> files) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        BoardRequestDTO boardRequest = objectMapper.readValue(boardRequestJson, BoardRequestDTO.class);

        if (boardRequest == null) {
            throw new BusinessException("BoardRequest is missing");
        }

        User user = userV2Service.getUserByUserNumReturnUser(2L);
        BoardMaster boardMaster = boardMasterService.getBoardMasterEntity(boardMasterId);
        BoardResponseDTO result = boardService.saveBoard(boardRequest, boardMaster, user, files);

        return ResponseEntity.ok(result);
    }
    @PutMapping(value ="/{boardId}", consumes = {"multipart/form-data"})
        public ResponseEntity<BoardResponseDTO> updateBoard(
                //@PathVariable("boardMasterId") Long boardMasterId,
                @PathVariable("boardId") Long boardId,
                @RequestParam("boardRequest") String boardRequestJson,
                @RequestPart(value = "files", required = false) List<MultipartFile> files,
                @RequestPart(value = "deleteFiles", required = false) String deleteFiles) throws JsonProcessingException {

            ObjectMapper objectMapper = new ObjectMapper();
            BoardRequestDTO boardRequest = objectMapper.readValue(boardRequestJson, BoardRequestDTO.class);

            if (boardRequest == null) {
                throw new BusinessException("BoardRequest is missing");
            }
            BoardResponseDTO updatedBoard = boardService.updateBoard(boardId, boardRequest, files, deleteFiles);
            return ResponseEntity.ok(updatedBoard);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> removeBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") Long fileId) throws IOException {
        BoardFile boardFile = boardService.fileDetailService(fileId);
        if (boardFile == null) {
            throw new NotFoundException("File not found with id: " + fileId);
        }

        String filePath = uploadConfig.getUploadDir() + boardFile.getPath();
        File file = new File(filePath);
        if (!file.exists()) {
            throw new BusinessException("FILE_NOT_FOUND", "Physical file not found: " + filePath);
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + boardFile.getOriName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.length())
                .body(resource);
    }
}
