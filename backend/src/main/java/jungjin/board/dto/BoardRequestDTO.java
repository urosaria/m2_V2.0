package jungjin.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoardRequestDTO {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Contents is required")
    private String contents;

//    private List<MultipartFile> files;
}
