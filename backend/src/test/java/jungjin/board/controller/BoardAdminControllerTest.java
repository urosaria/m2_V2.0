package jungjin.board.controller;

import jungjin.board.domain.Board;
import jungjin.board.domain.BoardMaster;
import jungjin.board.dto.BoardDto;
import jungjin.board.mapper.BoardMapper;
import jungjin.board.service.BoardMasterService;
import jungjin.board.service.BoardReplyService;
import jungjin.board.service.BoardService;
import jungjin.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BoardAdminControllerTest {

    @Mock
    private BoardService boardService;

    @Mock
    private BoardMasterService boardMasterService;

    @Mock
    private BoardReplyService boardReplyService;

    @Mock
    private BoardMapper boardMapper;

    @InjectMocks
    private BoardAdminController boardAdminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void boardList_Success() {
        // Arrange
        long boardMasterId = 1L;
        int page = 1;
        int size = 10;

        BoardMaster boardMaster = BoardMaster.builder()
            .id(boardMasterId)
            .name("Test Board")
            .status("S")
            .build();

        User user = User.builder()
            .name("Test User")
            .build();

        Board board = Board.builder()
            .id(1L)
            .title("Test Title")
            .contents("Test Contents")
            .user(user)
            .createDate(LocalDateTime.now())
            .boardMaster(boardMaster)
            .status("S")
            .build();

        BoardDto boardDto = BoardDto.builder()
            .id(board.getId())
            .title(board.getTitle())
            .contents(board.getContents())
            .writer(board.getUser().getName())
            .createDate(board.getCreateDate())
            .build();

        Page<Board> boardPage = new PageImpl<>(Arrays.asList(board));

        when(boardMasterService.showBoardMaster(boardMasterId)).thenReturn(boardMaster);
        when(boardService.listBoard(page, size, boardMasterId)).thenReturn(boardPage);
        when(boardMapper.toDto(any(Board.class))).thenReturn(boardDto);

        // Act
        ResponseEntity<?> response = boardAdminController.boardList();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void boardList_Error() {
        // Arrange
        long boardMasterId = 1L;
        int page = 1;
        int size = 10;

        when(boardMasterService.showBoardMaster(boardMasterId))
            .thenThrow(new RuntimeException("Test exception"));

        // Act
        ResponseEntity<?> response = boardAdminController.boardList();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
