package jungjin.board.controller;

import jungjin.board.domain.Board;
import jungjin.board.domain.BoardFile;
import jungjin.board.domain.BoardMaster;
import jungjin.board.service.BoardMasterService;
import jungjin.board.service.BoardService;

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
        try {
            Page<Board> noticeList = boardService.listBoard(page, 10, 1);
            Page<Board> communityList = boardService.listBoard(page, 10, 2);
            
            Map<String, Object> response = new HashMap<>();
            response.put("noticeList", noticeList);
            response.put("communityList", communityList);
            response.put("page", page);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching board list: " + e.getMessage());
        }
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
            return ResponseEntity.badRequest().body("Error fetching board contents: " + e.getMessage());
        }
    }

    @GetMapping("/show/{boardMasterId}/{boardId}")
    public ResponseEntity<?> getBoard(
            @PathVariable("boardMasterId") int boardMasterId,
            @PathVariable("boardId") Long boardId) {
        try {
            Board board = boardService.showBoard(boardId);
            return ResponseEntity.ok(board);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching board: " + e.getMessage());
        }
    }

    @GetMapping("/register/{boardMasterId}")
    public ResponseEntity<?> getBoardRegisterForm(
            @PathVariable("boardMasterId") int boardMasterId) {
        try {
            BoardMaster boardMaster = boardMasterService.showBoardMaster(boardMasterId);
            Map<String, Object> response = new HashMap<>();
            response.put("boardMaster", boardMaster);
            response.put("board", new Board());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error preparing board form: " + e.getMessage());
        }
    }

    @PostMapping("/register/{boardMasterId}")
    public ResponseEntity<?> createBoard(
            @PathVariable("boardMasterId") int boardMasterId,
            @RequestBody Board board) {
        try {
            board.setBoardMaster(boardMasterService.showBoardMaster(boardMasterId));
            boardService.saveBoard(board);
            Board savedBoard = boardService.showBoard(board.getId());
            return ResponseEntity.ok(savedBoard);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating board: " + e.getMessage());
        }
    }

    @GetMapping("/modify/{boardMasterId}/{boardId}")
    public ResponseEntity<?> getBoardForModify(
            @PathVariable("boardMasterId") int boardMasterId,
            @PathVariable("boardId") Long boardId) {
        try {
            Board board = boardService.showBoard(boardId);
            BoardMaster boardMaster = boardMasterService.showBoardMaster(boardMasterId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("board", board);
            response.put("boardMaster", boardMaster);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching board for modification: " + e.getMessage());
        }
    }

    @PutMapping("/modify/{boardMasterId}/{boardId}")
    public ResponseEntity<?> updateBoard(
            @PathVariable("boardMasterId") int boardMasterId,
            @PathVariable("boardId") Long boardId,
            @RequestBody Board board) {
        try {
            Board originBoard = boardService.showBoard(boardId);
            originBoard.setTitle(board.getTitle());
            originBoard.setContents(board.getContents());
            
            boardService.saveBoard(originBoard);
            Board updatedBoard = boardService.showBoard(boardId);
            
            return ResponseEntity.ok(updatedBoard);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating board: " + e.getMessage());
        }
    }

    @DeleteMapping("/remove/{boardId}")
    public ResponseEntity<?> removeBoard(@PathVariable("boardId") Long boardId) {
        try {
            Board board = boardService.deleteBoard(boardId);
            if (board == null) {
                return ResponseEntity.badRequest().body("Failed to delete board");
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting board: " + e.getMessage());
        }
    }

    @GetMapping("/file/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") Long fileId) {
        try {
            BoardFile boardFile = boardService.fileDetailService(fileId);
            String filePath = System.getProperty("user.home") + "/Downloads/upload/" + boardFile.getName();
            
            File file = new File(filePath);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + boardFile.getOriName() + "\"");
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
            headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()));
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
