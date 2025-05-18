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
    private String name;
    private String oriName;
    private String path;
    private Long size;

    public static BoardFileResponseDTO fromEntity(BoardFile file) {
        return BoardFileResponseDTO.builder()
                .id(file.getId())
                .name(file.getName())
                .oriName(file.getOriName())
                .path(file.getPath())
                .size(file.getSize())
                .build();
    }
}
