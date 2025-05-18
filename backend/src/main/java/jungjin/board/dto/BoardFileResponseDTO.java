package jungjin.board.dto;

import jungjin.board.domain.BoardFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardFileResponseDTO {
    private Long id;
    private String oriName;
    private String path;
    private Long size;
    private String ext;

    public static BoardFileResponseDTO fromEntity(BoardFile file) {
        return BoardFileResponseDTO.builder()
                .id(file.getId())
                .oriName(file.getOriName())
                .path(file.getPath())
                .size(file.getSize())
                .ext(file.getExt())
                .build();
    }
}
