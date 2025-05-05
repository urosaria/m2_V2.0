package jungjin.estimate.dto;

import jungjin.estimate.domain.Structure;
import jungjin.estimate.domain.StructureDetail;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EstimateRequestDTO {
    private Structure structure;
    private StructureDetail structureDetail;

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public StructureDetail getStructureDetail() {
        return structureDetail;
    }

    public void setStructureDetail(StructureDetail structureDetail) {
        this.structureDetail = structureDetail;
    }
}
