package jungjin.estimate.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class EstimateDetailRequestDTO {
    private String insideWallYn = "N";
    private String ceilingYn = "N";
    private String windowYn = "N";
    private String doorYn = "N";
    private String canopyYn = "N";
    private String downpipeYn = "N";
    private String insideWallType;
    private String insideWallPaper;
    private Integer insideWallThick;
    private String outsideWallType;
    private String outsideWallPaper;
    private Integer outsideWallThick;
    private String roofType;
    private String roofPaper;
    private Integer roofThick;
    private String ceilingType;
    private String ceilingPaper;
    private Integer ceilingThick;
    private Integer gucci;
    private Integer gucciInside;
    private Integer gucciInsideAmount;
    private Integer gucciAmount;

    private List<ComponentRequestDTO> canopyList = new ArrayList<>();
    private List<ComponentRequestDTO> ceilingList = new ArrayList<>();
    private List<ComponentRequestDTO> doorList = new ArrayList<>();
    private List<ComponentRequestDTO> downpipeList = new ArrayList<>();
    private List<ComponentRequestDTO> insideWallList = new ArrayList<>();
    private List<ComponentRequestDTO> windowList = new ArrayList<>();
}
