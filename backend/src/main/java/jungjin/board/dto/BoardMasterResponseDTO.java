package jungjin.board.dto;

import jungjin.board.domain.BoardMaster;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardMasterResponseDTO {
    private long id;
    private String name;
    private String replyYn;
    private String status;
    private String skinName;

    public static BoardMasterResponseDTO fromEntity(BoardMaster boardMaster) {
        return BoardMasterResponseDTO.builder()
                .id(boardMaster.getId())
                .name(boardMaster.getName())
                .replyYn(boardMaster.getReplyYn())
                .status(boardMaster.getStatus())
                .skinName(boardMaster.getSkinName())
                .build();
    }
}
