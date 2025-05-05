package jungjin.estimate.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "m2_est_calculate")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Calculate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "structure_id")
    private Structure structure;

    @Column(name = "name")
    private String name;

    @Column(name = "standard")
    private String standard;

    @Column(name = "unit")
    private String unit;

    @Column(name = "amount")
    private int amount;

    @Column(name = "price")
    private int uPrice;

    @Column(name = "type")
    private String type = "P";

    @Column(name = "e_price")
    private int ePrice;

    @Column(name = "total")
    private long total;

    @Column(name = "c_sort", nullable = false)
    private int sort;

}
