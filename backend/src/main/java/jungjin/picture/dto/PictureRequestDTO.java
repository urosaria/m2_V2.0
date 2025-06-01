package jungjin.picture.dto;

import jungjin.picture.domain.Picture;
import jungjin.user.domain.User;
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

    public Picture toEntity(User user) {
        return Picture.builder()
                .name(this.name)
                .etc(this.etc)
                .status(this.status)
                .user(user)
                .build();
    }
}
