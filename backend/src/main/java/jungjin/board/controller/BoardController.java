package jungjin.board.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jungjin.board.domain.BoardMaster;
import jungjin.board.dto.BoardRequestDTO;
import jungjin.board.dto.BoardResponseDTO;
import jungjin.board.service.BoardMasterService;
import jungjin.board.service.BoardService;
import jungjin.common.exception.BusinessException;
import jungjin.user.domain.User;
import jungjin.user.service.UserService;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final BoardMasterService boardMasterService;
    private final UserService userV2Service;

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
}
