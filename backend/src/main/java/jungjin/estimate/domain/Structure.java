package jungjin.estimate.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jungjin.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "m2_est_structure")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class Structure {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "city_name")
    private String cityName;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_num", nullable = false)
    private User user;

    @CreatedDate
    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;

    @Column(name = "status_date")
    private LocalDateTime statusDate;

    @Column(name = "status", length = 1, nullable = false)
    private String status = "S";

/*
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "structure")
    @OrderBy("sort ASC")
*/
    @OneToMany(mappedBy = "structure", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Calculate> calculateList = new ArrayList<>();

    @OneToOne(mappedBy = "structure", cascade = CascadeType.ALL, orphanRemoval = true)
    private StructureDetail structureDetail;
}
