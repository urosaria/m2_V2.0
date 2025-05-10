package jungjin.estimate.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EstimateRequestDTO {
    // For Structure
    private String title;
    private String cityName;
    private String placeName;
    private String structureType;
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

    // For structure detail
    private EstimateDetailRequestDTO structureDetail;
}
