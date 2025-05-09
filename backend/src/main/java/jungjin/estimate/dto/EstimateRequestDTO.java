package jungjin.estimate.dto;

import jakarta.validation.constraints.NotBlank;
import jungjin.estimate.domain.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class EstimateRequestDTO {
    // For Structure
    private String title;
    private CityCode cityName;
    @NotBlank(message = "Title is required.")
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
    private String insideWallYn = "N";
    private String ceilingYn = "N";
    private String windowYn = "N";
    private String doorYn = "N";
    private String canopyYn = "N";
    private String downpipeYn = "N";
    private String insideWallType;
    private String insideWallPaper;
    private int insideWallThick;
    private String outsideWallType;
    private String outsideWallPaper;
    private int outsideWallThick;
    private String roofType;
    private String roofPaper;
    private int roofThick;
    private String ceilingType;
    private String ceilingPaper;
    private int ceilingThick;
    private int gucci;
    private int gucciInside = 0;
    private int gucciInsideAmount = 0;
    private int gucciAmount;

    private List<ComponentRequestDTO> canopyList = new ArrayList<>();
    private List<ComponentRequestDTO> ceilingList = new ArrayList<>();
    private List<ComponentRequestDTO> doorList = new ArrayList<>();
    private List<ComponentRequestDTO> downpipeList = new ArrayList<>();
    private List<ComponentRequestDTO> insideWallList = new ArrayList<>();
    private List<ComponentRequestDTO> windowList = new ArrayList<>();
}
