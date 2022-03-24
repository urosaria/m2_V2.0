package jungjin.estimate.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "m2_est_canopy")
public class Canopy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "length")
    private int length;

    @Column(name = "amount")
    private int amount;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "structure_detail_id")
    private StructureDetail structureDetail;

    public void setId(long id) {
        this.id = id;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setStructureDetail(StructureDetail structureDetail) {
        this.structureDetail = structureDetail;
    }

    public long getId() {
        return this.id;
    }

    public int getLength() {
        return this.length;
    }

    public int getAmount() {
        return this.amount;
    }

    public StructureDetail getStructureDetail() {
        return this.structureDetail;
    }
}
