package jungjin.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequestDTO {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Contents is required")
    private String contents;

//    private List<MultipartFile> files;
}
