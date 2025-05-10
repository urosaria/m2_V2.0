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
    private int trussHeight;
    private int eavesLength;
    private int rearTrussHeight;
    private int insideWidth = 0;
    private int insideLength = 0;
    private int rooftopSideHeight = 0;
    private int rooftopWidth = 0;
    private int rooftopHeight = 0;
    private int rooftopLength = 0;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // For structure detail
    private EstimateDetailResponseDTO structureDetail;
    private List<CalculateDTO> calculateList = new ArrayList<>();
}
