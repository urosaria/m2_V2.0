package jungjin.board.controller;

import jungjin.board.domain.Board;
import jungjin.board.domain.BoardFile;
import jungjin.board.domain.BoardMaster;
import jungjin.board.service.BoardMasterService;
import jungjin.board.service.BoardService;
import jungjin.common.exception.NotFoundException;
import jungjin.common.exception.BusinessException;

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
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final BoardMasterService boardMasterService;

    @GetMapping("/list")
    public ResponseEntity<?> getBoardList(
            @RequestParam(defaultValue = "1") int page) {
        Page<Board> noticeList = boardService.listBoard(page, 10, 1);
        Page<Board> communityList = boardService.listBoard(page, 10, 2);
        
        Map<String, Object> response = new HashMap<>();
        response.put("noticeList", noticeList);
        response.put("communityList", communityList);
        response.put("page", page);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list/contents")
    public ResponseEntity<?> getBoardContents(
            @RequestParam(defaultValue = "1") int page) {
        try {
            Page<Board> noticeList = boardService.listBoard(page, 10, 1);
            
            Map<String, Object> response = new HashMap<>();
            response.put("noticeList", noticeList);
            response.put("page", page);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new BusinessException("Error fetching board contents: " + e.getMessage());
        }
    }

    @GetMapping("/show/{boardMasterId}/{boardId}")
    public ResponseEntity<?> getBoard(
            @PathVariable("boardMasterId") int boardMasterId,
            @PathVariable("boardId") Long boardId) {
        try {
            Board board = boardService.showBoard(boardId);
            if (board == null) {
                throw new NotFoundException("Board not found with id: " + boardId);
            }
            if (board.getBoardMaster().getId() != boardMasterId) {
                throw new BusinessException("Board does not belong to board master: " + boardMasterId);
            }
            return ResponseEntity.ok(board);
        } catch (Exception e) {
            throw new BusinessException("Error fetching board: " + e.getMessage());
        }
    }

    @GetMapping("/register/{boardMasterId}")
    public ResponseEntity<?> getBoardRegisterForm(
            @PathVariable("boardMasterId") int boardMasterId) {
        try {
            BoardMaster boardMaster = boardMasterService.showBoardMaster(boardMasterId);
            if (boardMaster == null) {
                throw new NotFoundException("Board master not found with id: " + boardMasterId);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("boardMaster", boardMaster);
            response.put("board", new Board());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new BusinessException("Error preparing board form: " + e.getMessage());
        }
    }

    @PostMapping("/register/{boardMasterId}")
    public ResponseEntity<?> createBoard(
            @PathVariable("boardMasterId") int boardMasterId,
            @RequestBody Board board) {
        try {
            boardService.saveBoard(board);
            Board savedBoard = boardService.showBoard(board.getId());
            return ResponseEntity.ok(savedBoard);
        } catch (Exception e) {
            throw new BusinessException("Error creating board: " + e.getMessage());
        }
    }

    @GetMapping("/modify/{boardMasterId}/{boardId}")
    public ResponseEntity<?> getBoardForModify(
            @PathVariable("boardMasterId") int boardMasterId,
            @PathVariable("boardId") Long boardId) {
        try {
            Board board = boardService.showBoard(boardId);
            if (board == null) {
                throw new NotFoundException("Board not found with id: " + boardId);
            }
            if (board.getBoardMaster().getId() != boardMasterId) {
                throw new BusinessException("Board does not belong to board master: " + boardMasterId);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("board", board);
            response.put("boardMaster", board.getBoardMaster());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new BusinessException("Error fetching board for modification: " + e.getMessage());
        }
    }

    @PutMapping("/modify/{boardMasterId}/{boardId}")
    public ResponseEntity<?> updateBoard(
            @PathVariable("boardMasterId") int boardMasterId,
            @PathVariable("boardId") Long boardId,
            @RequestBody Board board) {
        try {
            Board originBoard = boardService.showBoard(boardId);
            if (originBoard == null) {
                throw new NotFoundException("Board not found with id: " + boardId);
            }
            if (originBoard.getBoardMaster().getId() != boardMasterId) {
                throw new BusinessException("Board does not belong to board master: " + boardMasterId);
            }
            originBoard.setTitle(board.getTitle());
            originBoard.setContents(board.getContents());
            
            boardService.saveBoard(originBoard);
            Board updatedBoard = boardService.showBoard(boardId);
            
            return ResponseEntity.ok(updatedBoard);
        } catch (Exception e) {
            throw new BusinessException("Error updating board: " + e.getMessage());
        }
    }

    @DeleteMapping("/remove/{boardId}")
    public ResponseEntity<?> removeBoard(@PathVariable("boardId") Long boardId) {
        try {
            Board board = boardService.deleteBoard(boardId);
            if (board == null) {
                throw new NotFoundException("Board not found with id: " + boardId);
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new BusinessException("Error deleting board: " + e.getMessage());
        }
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") Long fileId) throws IOException {
        try {
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
        } catch (Exception e) {
            throw new BusinessException("Error downloading file: " + e.getMessage());
        }
    }
}
