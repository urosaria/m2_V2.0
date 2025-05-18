package jungjin.board.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import jungjin.board.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDTO {
    private Long id;
    private String title;
    private String contents;
    private int readcount;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private BoardMasterResponseDTO boardMaster;
    private List<BoardFileResponseDTO> files;
    private String userName;
    private Long userNum;
}
