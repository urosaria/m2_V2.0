package jungjin.estimate.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EstimateExcelResponseDTO {
    private Long id;
    private String name;
    private String oriName;
    private String ext;
    private String path;
    private Long totalPrice;
    private LocalDateTime createDate;
}