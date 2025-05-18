package jungjin.board.controller;

import jakarta.validation.Valid;
import jungjin.board.domain.BoardFile;
import jungjin.board.domain.BoardMaster;
import jungjin.board.dto.BoardRequestDTO;
import jungjin.board.dto.BoardResponseDTO;
import jungjin.board.service.BoardMasterService;
import jungjin.board.service.BoardService;
import jungjin.common.exception.NotFoundException;
import jungjin.common.exception.BusinessException;

import jungjin.user.domain.User;
import jungjin.user.service.UserV2Service;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final BoardMasterService boardMasterService;
    private final UserV2Service userV2Service;

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

    @PostMapping(value = "/{boardMasterId}", consumes = {"multipart/form-data"})
    public ResponseEntity<BoardResponseDTO> createBoard(
            @PathVariable("boardMasterId") Long boardMasterId,
            @Valid @RequestBody BoardRequestDTO boardRequest) {
        //TODO: need to change to retrieve logging user
        User user = userV2Service.getUserByUserNumReturnUser(2L);
        BoardMaster boardMaster = boardMasterService.getBoardMasterEntity(boardMasterId);
        BoardResponseDTO result = boardService.saveBoard(boardRequest, boardMaster, user);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{boardMasterId}/{boardId}")
    public ResponseEntity<BoardResponseDTO> updateBoard(
            @PathVariable("boardMasterId") Long boardMasterId,
            @PathVariable("boardId") Long boardId,
            @Valid @RequestBody BoardRequestDTO boardRequest) {

        BoardResponseDTO updatedBoard = boardService.updateBoard(boardMasterId, boardId, boardRequest);
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

        String filePath = System.getProperty("user.home") + "/Downloads/upload/" + boardFile.getName();
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
