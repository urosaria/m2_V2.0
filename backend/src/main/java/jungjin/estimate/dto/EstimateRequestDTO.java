package jungjin.estimate.dto;

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
    private Integer trussHeight;
    private Integer eavesLength;
    private Integer rearTrussHeight;
    private Integer insideWidth;
    private Integer insideLength;
    private Integer rooftopSideHeight;
    private Integer rooftopWidth;
    private Integer rooftopHeight;
    private Integer rooftopLength;

    // For structure detail
    private EstimateDetailRequestDTO structureDetail;
}
