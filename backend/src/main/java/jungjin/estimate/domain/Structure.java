package jungjin.estimate.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jungjin.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "m2_est_structure")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Structure {

    public Structure setUser(User user) {
        this.user = user;
        return this;
    }

    public Structure setId(long id) {
        this.id = id;
        return this;
    }

    public Structure setCalculateList(List<Calculate> calculateList) {
        this.calculateList = calculateList;
        return this;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "status", length = 1, nullable = false)
    private String status = "S";

    @Column(name = "place_name")
    private String placeName;

    @Column(name = "structure_type")
    private String structureType;

    @Column(name = "width")
    private int width;

    @Column(name = "length")
    private int length;

    @Column(name = "height")
    private int height;

    @Column(name = "truss_height")
    private int trussHeight;

    @Column(name = "eaves_length")
    private int eavesLength;

    @Column(name = "rear_truss_height")
    private int rearTrussHeight;

    @Column(name = "inside_width", nullable = false)
    private int insideWidth = 0;

    @Column(name = "inside_length", nullable = false)
    private int insideLength = 0;

    @Column(name = "rooftop_side_height", nullable = false)
    private int rooftopSideHeight = 0;

    @Column(name = "rooftop_width", nullable = false)
    private int rooftopWidth = 0;

    @Column(name = "rooftop_height", nullable = false)
    private int rooftopHeight = 0;

    @Column(name = "rooftop_length", nullable = false)
    private int rooftopLength = 0;

    @NotNull
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_est_structure_writer"))
    private User user;

    @NotNull
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "status_date")
    private LocalDateTime statusDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "structure")
    @OrderBy("sort ASC")
    private List<Calculate> calculateList = new ArrayList<>();

    @Transient
    private StructureDetail structureDetail;
}
