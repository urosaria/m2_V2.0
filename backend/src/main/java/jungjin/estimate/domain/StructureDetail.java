package jungjin.estimate.domain;

import jakarta.persistence.*;
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
@Table(name = "m2_est_structure_detail")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class StructureDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "inside_wall_yn")
    private String insideWallYn = "N";

    @Column(name = "ceiling_yn")
    private String ceilingYn = "N";

    @Column(name = "window_yn")
    private String windowYn = "N";

    @Column(name = "door_yn")
    private String doorYn = "N";

    @Column(name = "canopy_yn")
    private String canopyYn = "N";

    @Column(name = "downpipe_yn")
    private String downpipeYn = "N";

    @Convert(converter = InsulationTypeCodeConverter.class)
    @Column(name = "inside_wall_type")
    private InsulationTypeCode insideWallType;

    @Convert(converter = InsulationSubTypeCodeConverter.class)
    @Column(name = "inside_wall_paper")
    private InsulationSubTypeCode insideWallPaper;

    @Column(name = "inside_wall_thick")
    private Integer insideWallThick = 0;

    @Convert(converter = InsulationTypeCodeConverter.class)
    @Column(name = "outside_wall_type")
    private InsulationTypeCode outsideWallType;

    @Convert(converter = InsulationSubTypeCodeConverter.class)
    @Column(name = "outside_wall_paper")
    private InsulationSubTypeCode outsideWallPaper;

    @Column(name = "outside_wall_thick")
    private Integer outsideWallThick = 0;

    @Convert(converter = InsulationTypeCodeConverter.class)
    @Column(name = "roof_type")
    private InsulationTypeCode roofType;

    @Convert(converter = InsulationSubTypeCodeConverter.class)
    @Column(name = "roof_paper")
    private InsulationSubTypeCode roofPaper;

    @Column(name = "roof_thick")
    private Integer roofThick = 0;

    @Convert(converter = InsulationTypeCodeConverter.class)
    @Column(name = "ceiling_type")
    private InsulationTypeCode ceilingType;

    @Convert(converter = InsulationSubTypeCodeConverter.class)
    @Column(name = "ceiling_paper")
    private InsulationSubTypeCode ceilingPaper;

    @Column(name = "ceiling_thick")
    private Integer ceilingThick = 0;

    @OneToOne
    @JoinColumn(name = "structure_id")
    private Structure structure;

    @CreatedDate
    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "gucci")
    private Integer gucci = 0;

    @Column(name = "gucci_inside")
    private Integer gucciInside = 0;

    @Column(name = "gucci_inside_amount")
    private Integer gucciInsideAmount = 0;

    @Column(name = "gucci_amount")
    private Integer gucciAmount = 0;

    @OneToMany(mappedBy = "structureDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Canopy> canopyList = new ArrayList<>();

    @OneToMany(mappedBy = "structureDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ceiling> ceilingList = new ArrayList<>();

    @OneToMany(mappedBy = "structureDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Door> doorList = new ArrayList<>();

    @OneToMany(mappedBy = "structureDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Downpipe> downpipeList = new ArrayList<>();

    @OneToMany(mappedBy = "structureDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InsideWall> insideWallList = new ArrayList<>();

    @OneToMany(mappedBy = "structureDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Window> windowList = new ArrayList<>();
}