package jungjin.picture.dto;

import jungjin.picture.domain.Picture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PictureResponseDTO {
    private Long id;
    private String name;
    private String etc;
    private String status = "S1";
    private LocalDateTime createDate;

    public static PictureResponseDTO fromPicture(Picture picture) {
        return new PictureResponseDTO(
                picture.getId(),
                picture.getName(),
                picture.getEtc(),
                picture.getStatus(),
                picture.getCreateDate()
        );
    }
}
