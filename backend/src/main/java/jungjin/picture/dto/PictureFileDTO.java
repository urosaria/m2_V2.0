package jungjin.picture.dto;

import jungjin.picture.domain.PictureAdminFile;
import jungjin.picture.domain.PictureFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PictureFileDTO {
    private Long id;
    private String oriName;
    private String ext;
    private String path;

    public static PictureFileDTO from(PictureFile file) {
        return PictureFileDTO.builder()
                .id(file.getId())
                .oriName(file.getOriName())
                .ext(file.getExt())
                .path(file.getPath())
                .build();
    }

    public static PictureFileDTO from(PictureAdminFile file) {
        return PictureFileDTO.builder()
                .id(file.getId())
                .oriName(file.getOriName())
                .ext(file.getExt())
                .path(file.getPath())
                .build();
    }
}