package jungjin.board.controller;

import jungjin.board.domain.BoardMaster;
import jungjin.board.service.BoardMasterService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/board/master")
@RequiredArgsConstructor
public class BoardMasterController {
    private final BoardMasterService boardMasterService;

    @GetMapping("/list")
    public ResponseEntity<?> getBoardMasterList() {
        try {
            List<BoardMaster> masterList = boardMasterService.listBoardMaster();
            return ResponseEntity.ok(masterList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching board master list: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> createBoardMaster(@RequestBody BoardMaster boardMaster) {
        try {
            boardMasterService.saveBoardMaster(boardMaster);
            return ResponseEntity.ok(boardMaster);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating board master: " + e.getMessage());
        }
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<?> getBoardMaster(@PathVariable("id") int id) {
        try {
            BoardMaster boardMaster = boardMasterService.showBoardMaster(id);
            return ResponseEntity.ok(boardMaster);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching board master: " + e.getMessage());
        }
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<?> updateBoardMaster(
            @PathVariable("id") int id,
            @RequestBody BoardMaster boardMaster) {
        try {
            boardMasterService.updateBoardMaster(id, boardMaster);
            BoardMaster updatedMaster = boardMasterService.showBoardMaster(id);
            return ResponseEntity.ok(updatedMaster);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating board master: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBoardMaster(@PathVariable("id") int id) {
        try {
            BoardMaster boardMaster = boardMasterService.deleteBoardMaster(id);
            if (boardMaster == null) {
                return ResponseEntity.badRequest().body("Failed to delete board master");
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting board master: " + e.getMessage());
        }
    }
}
