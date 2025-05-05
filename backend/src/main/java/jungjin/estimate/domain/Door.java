package jungjin.estimate.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "m2_est_door")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Door {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "width")
    private int width;

    @Column(name = "height")
    private int height;

    @Column(name = "amount")
    private int amount;

    @Column(name = "door_type")
    private String type;

    @Column(name = "door_type_sub")
    private String subType;

    @Transient
    private String selectWh;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "structure_detail_id")
    private StructureDetail structureDetail;
}
