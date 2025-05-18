package jungjin.board.mapper;

import jungjin.board.domain.Board;
import jungjin.board.domain.BoardMaster;
import jungjin.board.dto.*;
import jungjin.user.domain.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BoardMapper {
    public Board toEntity(BoardRequestDTO dto, BoardMaster boardMaster, User user) {
        return Board.builder()
                .title(dto.getTitle())
                .contents(dto.getContents())
                .boardMaster(boardMaster)
                .user(user)
                .build();
    }

    public BoardResponseDTO toDto(Board board) {
        return BoardResponseDTO.builder()
                .id(board.getId())
                .title(board.getTitle())
                .contents(board.getContents())
                .readcount(board.getReadcount())
                .createDate(board.getCreateDate())
                .updateDate(board.getUpdateDate())
                .boardMaster(BoardMasterResponseDTO.fromEntity(board.getBoardMaster()))
                .files(board.getFiles() != null ? board.getFiles().stream()
                        .map(BoardFileResponseDTO::fromEntity)
                        .collect(Collectors.toList()) : null)
                .userName(board.getUser() != null ? board.getUser().getName() : null)
                .userNum(board.getUser() != null ? board.getUser().getNum() : null)
                .build();
    }
}
