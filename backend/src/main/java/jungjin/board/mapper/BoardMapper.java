package jungjin.board.mapper;

import jungjin.board.domain.Board;
import jungjin.board.dto.BoardDto;
import org.springframework.stereotype.Component;

@Component
public class BoardMapper {
    public BoardDto toDto(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .contents(board.getContents())
                .writer(board.getUser() != null ? board.getUser().getName() : "")
                .createDate(board.getCreateDate())
                .build();
    }
}
