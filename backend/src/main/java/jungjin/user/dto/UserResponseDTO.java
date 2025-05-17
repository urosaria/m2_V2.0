package jungjin.user.dto;

import jungjin.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long num;
    private String id;
    private String name;
    private String email;
    private String phone;
    private String agreeYn;
    private String companyName;
    private String companyAddress;
    private String companyPhone;
    private String companyWebsite;
    private String status;
    private String createDate;

    public static UserResponseDTO fromUser(User user) {
        return new UserResponseDTO(
                user.getNum(),
                user.getLoginId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getAgreeYn(),
                user.getCompanyName(),
                user.getCompanyAddress(),
                user.getCompanyPhone(),
                user.getCompanyWebsite(),
                user.getStatus(),
                user.getCreateDate() != null ? user.getCreateDate().toString() : null
        );
    }
}
