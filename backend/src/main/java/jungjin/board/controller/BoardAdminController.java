package jungjin.board.controller;

import jungjin.board.domain.Board;
import jungjin.board.domain.BoardFile;
import jungjin.board.domain.BoardMaster;
import jungjin.board.domain.BoardReply;
import jungjin.board.dto.BoardDto;
import jungjin.board.mapper.BoardMapper;
import jungjin.board.service.BoardMasterService;
import jungjin.board.service.BoardReplyService;
import jungjin.board.service.BoardService;
import jungjin.config.UploadConfig;
import jungjin.user.service.UserCustom;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//TODO: might delete
@RestController
@RequestMapping("/api/admin/board")
@RequiredArgsConstructor
public class BoardAdminController {
    private final BoardService boardService;
    private final BoardMasterService boardMasterService;
    private final BoardReplyService boardReplyService;
    private final BoardMapper boardMapper;
    private final UploadConfig uploadConfig;

    @GetMapping("/list")
    public ResponseEntity<?> boardList() {
        try {
            List<BoardMaster> boardMasters = boardMasterService.listBoardMaster();
            return ResponseEntity.ok(boardMasters);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error fetching board list", "message", e.getMessage()));
        }
    }

    @GetMapping("/detail/{boardMasterId}")
    public ResponseEntity<?> getBoardMaster(
            @PathVariable("boardMasterId") long boardMasterId) {
        try {
            BoardMaster boardMaster = boardMasterService.showBoardMaster(boardMasterId);
            return ResponseEntity.ok(boardMaster);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching board master: " + e.getMessage());
        }
    }

    @PostMapping("/register/{boardMasterId}")
    public ResponseEntity<?> boardInsert(
            @RequestPart("board") Board board,
            @PathVariable("boardMasterId") int boardMasterId,
            @RequestPart(value = "files", required = false) MultipartFile[] files) {
        try {
            UserCustom principal = (UserCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            board.setUser(principal.getUser());
            board.setBoardMaster(boardMasterService.showBoardMaster(boardMasterId));
            
            boardService.saveBoard(board);
            Board savedBoard = boardService.showBoard(board.getId());
            
            if (files != null && files.length > 0) {
                for (MultipartFile file : files) {
                    if (!file.isEmpty()) {
                        String fileName = file.getOriginalFilename();
                        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
                        String filePath = uploadConfig.getUploadDir() + "/" + System.currentTimeMillis() + "." + fileExt;

                        Path path = Paths.get(filePath);
                        Files.write(path, file.getBytes());
                        
                        BoardFile boardFile = new BoardFile();
                        boardFile.setBoard(savedBoard);
                        boardFile.setOriName(fileName);
                        boardFile.setName(fileName);
                        boardFile.setExt(fileExt);
                        boardFile.setCreateDate(java.time.LocalDateTime.now());
                        boardService.saveBoardFile(boardFile);
                    }
                }
            }
            
            return ResponseEntity.ok(savedBoard);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("File upload failed: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating board: " + e.getMessage());
        }
    }

    @GetMapping("/show/{boardMasterId}/{boardId}")
    public ResponseEntity<?> getBoard(
            @PathVariable("boardMasterId") int boardMasterId,
            @PathVariable("boardId") Long boardId) {
        try {
            Board board = boardService.showBoard(boardId);
            BoardMaster boardMaster = boardMasterService.showBoardMaster(boardMasterId);
            List<BoardReply> boardReplyList = boardReplyService.listBoardReply(boardId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("board", board);
            response.put("boardMaster", boardMaster);
            response.put("replyList", boardReplyList);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching board: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{boardMasterId}/{boardId}")
    public ResponseEntity<?> deleteBoard(
            @PathVariable("boardMasterId") int boardMasterId,
            @PathVariable("boardId") Long boardId) {
        try {
            boardService.deleteBoard(boardId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting board: " + e.getMessage());
        }
    }

    @PutMapping("/update/{boardMasterId}/{boardId}")
    public ResponseEntity<?> updateBoardWithFiles(
            @PathVariable("boardMasterId") int boardMasterId,
            @PathVariable("boardId") Long boardId,
            @RequestPart("board") Board board,
            @RequestPart(value = "files", required = false) MultipartFile[] files,
            @RequestParam(value = "fileDeleteIds", required = false) List<Long> fileDeleteIds) {
        try {
            Board originBoard = boardService.showBoard(boardId);
            originBoard.setTitle(board.getTitle());
            originBoard.setContents(board.getContents());
            
            boardService.saveBoard(originBoard);
            
            if (fileDeleteIds != null && !fileDeleteIds.isEmpty()) {
                boardService.deleteBoardFile(fileDeleteIds.toArray(new Long[0]));
            }
            
            if (files != null && files.length > 0) {
                for (MultipartFile file : files) {
                    if (!file.isEmpty()) {
                        String fileName = file.getOriginalFilename();
                        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
                        String filePath = uploadConfig.getUploadDir() + System.currentTimeMillis() + "." + fileExt;
                        
                        Path path = Paths.get(filePath);
                        Files.write(path, file.getBytes());
                        
                        BoardFile boardFile = new BoardFile();
                        boardFile.setBoard(originBoard);
                        boardFile.setOriName(fileName);
                        boardFile.setName(fileName);
                        boardFile.setExt(fileExt);
                        boardService.saveBoardFile(boardFile);
                    }
                }
            }
            
            Board updatedBoard = boardService.showBoard(boardId);
            return ResponseEntity.ok(updatedBoard);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating board: " + e.getMessage());
        }
    }

    @DeleteMapping("/remove/{boardMasterId}/{boardId}")
    public ResponseEntity<?> removeBoard(
            @PathVariable("boardMasterId") int boardMasterId,
            @PathVariable("boardId") Long boardId) {
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

    @PostMapping("/reply/{boardMasterId}/{boardId}")
    public ResponseEntity<?> createReply(
            @PathVariable("boardMasterId") int boardMasterId,
            @PathVariable("boardId") int boardId,
            @RequestBody BoardReply boardReply) {
        try {
            UserCustom principal = (UserCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            boardReply.setUser(principal.getUser());
            
            Board board = boardService.showBoard(Long.valueOf(boardId));
            boardReply.setBoard(board);
            
            boardReplyService.saveBoardReply(boardReply);
            BoardReply savedReply = boardReplyService.showBoardReply(boardReply.getId());
            return ResponseEntity.ok(savedReply);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating reply: " + e.getMessage());
        }
    }

    @GetMapping("/reply/{boardMasterId}/{replyId}")
    public ResponseEntity<?> getReply(
            @PathVariable("boardMasterId") int boardMasterId,
            @PathVariable("replyId") int replyId) {
        try {
            BoardReply reply = boardReplyService.showBoardReply(Long.valueOf(replyId));
            return ResponseEntity.ok(reply);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching reply: " + e.getMessage());
        }
    }

    @PutMapping("/reply/{boardMasterId}/{replyId}")
    public ResponseEntity<?> updateReply(
            @PathVariable("boardMasterId") int boardMasterId,
            @PathVariable("replyId") int replyId,
            @RequestBody BoardReply boardReply) {
        try {
            BoardReply existingReply = boardReplyService.showBoardReply(Long.valueOf(replyId));
            existingReply.setContents(boardReply.getContents());
            
            boardReplyService.saveBoardReply(existingReply);
            BoardReply updatedReply = boardReplyService.showBoardReply(existingReply.getId());
            return ResponseEntity.ok(updatedReply);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating reply: " + e.getMessage());
        }
    }

    @GetMapping({"/reply/remove/{id}"})
    @ResponseBody
    public String boardReplyRemove(@PathVariable Long id) {
        BoardReply boardReply = this.boardReplyService.deleteBoardReply(id);
        String success = "success";
        if (boardReply == null)
            success = "fail";
        return success;
    }
}
