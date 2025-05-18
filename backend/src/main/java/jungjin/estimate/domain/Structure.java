package jungjin.estimate.domain;

import jakarta.persistence.*;
import jungjin.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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

    @Convert(converter = CityCodeConverter.class)
    @Column(name = "city_name")
    private CityCode cityName;

    @Column(name = "place_name")
    private String placeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "structure_type")
    private StructureTypeCode structureType;

    @Column(name = "width")
    private int width;

    @Column(name = "length")
    private int length;

    @Column(name = "height")
    private int height;

    @Column(name = "truss_height")
    private Integer trussHeight;

    @Column(name = "eaves_length")
    private Integer eavesLength;

    @Column(name = "rear_truss_height")
    private Integer rearTrussHeight;

    @Column(name = "inside_width")
    private Integer insideWidth;

    @Column(name = "inside_length")
    private Integer insideLength;

    @Column(name = "rooftop_side_height")
    private Integer rooftopSideHeight;

    @Column(name = "rooftop_width")
    private Integer rooftopWidth;

    @Column(name = "rooftop_height")
    private Integer rooftopHeight;

    @Column(name = "rooftop_length")
    private Integer rooftopLength;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_num", nullable = false)
    private User user;

    @CreatedDate
    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "update_date")
    private LocalDateTime updateDate;

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
