package jungjin.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotBlank(message = "Id is required")
    private String id;

    @NotBlank(message = "Password is required")
    private String password;
}
