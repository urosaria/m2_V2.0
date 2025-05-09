package jungjin.estimate.dto;

import jungjin.estimate.domain.Structure;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EstimateListDTO {
    private long id;
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
    private int insideWidth;
    private int insideLength;
    private int rooftopSideHeight;
    private int rooftopWidth;
    private int rooftopHeight;
    private int rooftopLength;
    private LocalDateTime createDate;
    private LocalDateTime statusDate;
    private Long userNum;
    private String userName;
    private String status;

    public static EstimateListDTO from(Structure structure) {
        EstimateListDTO dto = new EstimateListDTO();
        dto.setId(structure.getId());
        dto.setTitle(structure.getTitle());
        dto.setStatus(structure.getStatus());
        dto.setCityName(structure.getCityName().getCode());
        dto.setPlaceName(structure.getPlaceName());
        dto.setStructureType(structure.getStructureType().name());
        dto.setWidth(structure.getWidth());
        dto.setLength(structure.getLength());
        dto.setHeight(structure.getHeight());
        dto.setTrussHeight(structure.getTrussHeight());
        dto.setEavesLength(structure.getEavesLength());
        dto.setRearTrussHeight(structure.getRearTrussHeight());
        dto.setInsideWidth(structure.getInsideWidth());
        dto.setInsideLength(structure.getInsideLength());
        dto.setRooftopSideHeight(structure.getRooftopSideHeight());
        dto.setRooftopWidth(structure.getRooftopWidth());
        dto.setRooftopHeight(structure.getRooftopHeight());
        dto.setRooftopLength(structure.getRooftopLength());
        dto.setCreateDate(structure.getCreateDate());
        dto.setStatusDate(structure.getStatusDate());
        if (structure.getUser() != null) {
            dto.setUserNum(structure.getUser().getNum());
            dto.setUserName(structure.getUser().getName());
        }
        return dto;
    }
}
