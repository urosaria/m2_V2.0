package jungjin.picture.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PictureRequestDTO {
    private String name;

    private String etc;

    private String status = "S1";
}
