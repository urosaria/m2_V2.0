package jungjin.board.controller;

import jungjin.board.dto.BoardMasterRequestDTO;
import jungjin.board.dto.BoardMasterResponseDTO;
import jungjin.board.service.BoardMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board/master")
@RequiredArgsConstructor
public class BoardMasterController {

    private final BoardMasterService boardMasterService;

    @GetMapping("/list")
    public ResponseEntity<List<BoardMasterResponseDTO>> getBoardMasterList() {
        return ResponseEntity.ok(boardMasterService.listBoardMasters());
    }

    @PostMapping
    public ResponseEntity<BoardMasterResponseDTO> createBoardMaster(@RequestBody BoardMasterRequestDTO dto) {
        BoardMasterResponseDTO created = boardMasterService.createBoardMaster(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardMasterResponseDTO> getBoardMaster(@PathVariable Long id) {
        BoardMasterResponseDTO boardMaster = boardMasterService.getBoardMasterById(id);
        return ResponseEntity.ok(boardMaster);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardMasterResponseDTO> updateBoardMaster(
            @PathVariable Long id,
            @RequestBody BoardMasterRequestDTO dto) {
        BoardMasterResponseDTO updated = boardMasterService.updateBoardMaster(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoardMaster(@PathVariable Long id) {
        boardMasterService.deleteBoardMaster(id);
        return ResponseEntity.noContent().build();
    }
}