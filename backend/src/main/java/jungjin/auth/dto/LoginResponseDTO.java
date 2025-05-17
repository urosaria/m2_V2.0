package jungjin.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDTO {
    private String access_token;
    private String token_type;
    private String expires_at;
    private long expires_in;
    private String id;
    private String role;
    private String name;
}
