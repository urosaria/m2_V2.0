package jungjin.estimate.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "m2_est_inside_wall")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class InsideWall {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "length")
    private int length;

    @Column(name = "height")
    private int height;

    @Column(name = "amount")
    private int amount;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "structure_detail_id")
    private StructureDetail structureDetail;

    public void insert(InsideWall insideWall) {
        this.length = insideWall.length;
        this.height = insideWall.height;
        this.amount = insideWall.amount;
        this.structureDetail = insideWall.structureDetail;
    }

    public int getWidth() {
        return length;
    }
}