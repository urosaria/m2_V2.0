package jungjin.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardMasterRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Reply YN is required")
    private String replyYn;

    @NotNull(message = "Status is required")
    private String status;

    private String skinName;
}
