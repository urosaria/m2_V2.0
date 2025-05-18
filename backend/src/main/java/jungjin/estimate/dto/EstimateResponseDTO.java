package jungjin.estimate.dto;

import jungjin.estimate.domain.*;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class EstimateResponseDTO {
    // For Structure
    private Long id;
    private String title;
    private String cityName;
    private String placeName;
    private StructureTypeCode structureType;
    private int width;
    private int length;
    private int height;
    private Integer trussHeight;
    private Integer eavesLength;
    private Integer rearTrussHeight;
    private Integer insideWidth;
    private Integer insideLength;
    private Integer rooftopSideHeight;
    private Integer rooftopWidth;
    private Integer rooftopHeight;
    private Integer rooftopLength;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // For structure detail
    private EstimateDetailResponseDTO structureDetail;
    private List<CalculateDTO> calculateList = new ArrayList<>();
}
