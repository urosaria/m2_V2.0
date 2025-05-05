package jungjin.estimate.dto;

import jungjin.estimate.domain.StructureDetail;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
public class EstimateResponseDTO {
    private int canopyTotal;
    private int ceilingTotal;
    private int doorTotal;
    private int insideWallTotal;
    private int windowTotal;
    private int doorPriceCount;
    private StructureDetail structureDetail;

}
