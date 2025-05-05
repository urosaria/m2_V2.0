package jungjin.estimate.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "m2_est_downpipe")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Downpipe {

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

    @NotNull
    @ManyToOne
    @JoinColumn(name = "structure_detail_id")
    private StructureDetail structureDetail;
}
