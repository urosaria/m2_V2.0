package jungjin.estimate.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "m2_est_price")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "gubun")
    private String gubun;

    @Column(name = "sub_gubun")
    private String subGubun;

    @Column(name = "type")
    private String type;

    @Column(name = "sub_type")
    private String subType;

    @Column(name = "start_price")
    private int startPrice;

    @Column(name = "gap_price")
    private int gapPrice;

    @Column(name = "max_thick_price")
    private int maxThickPrice = 0;

    @Column(name = "standard_price")
    private int standardPrice = 0;

    @Column
    private Integer ePrice = 0;
}