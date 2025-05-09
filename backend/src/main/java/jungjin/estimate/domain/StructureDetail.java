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
    private int insideWallThick;

    @Convert(converter = InsulationTypeCodeConverter.class)
    @Column(name = "outside_wall_type")
    private InsulationTypeCode outsideWallType;

    @Convert(converter = InsulationSubTypeCodeConverter.class)
    @Column(name = "outside_wall_paper")
    private InsulationSubTypeCode outsideWallPaper;

    @Column(name = "outside_wall_thick")
    private int outsideWallThick;

    @Convert(converter = InsulationTypeCodeConverter.class)
    @Column(name = "roof_type")
    private InsulationTypeCode roofType;

    @Convert(converter = InsulationSubTypeCodeConverter.class)
    @Column(name = "roof_paper")
    private InsulationSubTypeCode roofPaper;

    @Column(name = "roof_thick")
    private int roofThick;

    @Convert(converter = InsulationTypeCodeConverter.class)
    @Column(name = "ceiling_type")
    private InsulationTypeCode ceilingType;

    @Convert(converter = InsulationSubTypeCodeConverter.class)
    @Column(name = "ceiling_paper")
    private InsulationSubTypeCode ceilingPaper;

    @Column(name = "ceiling_thick")
    private int ceilingThick;

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
    private int gucci;

    @Column(name = "gucci_inside", nullable = false)
    private int gucciInside = 0;

    @Column(name = "gucci_inside_amount", nullable = false)
    private int gucciInsideAmount = 0;

    @Column(name = "gucci_amount")
    private int gucciAmount;

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

    public void insert(StructureDetail structureDetail) {
        //this.createDate = LocalDateTime.now();
        this.id = structureDetail.id;
        this.insideWallYn = structureDetail.insideWallYn;
        this.ceilingYn = structureDetail.ceilingYn;
        this.windowYn = structureDetail.windowYn;
        this.doorYn = structureDetail.doorYn;
        this.canopyYn = structureDetail.canopyYn;
        this.downpipeYn = structureDetail.downpipeYn;
        this.structure = structureDetail.structure;
    }

    public void updateGucci(StructureDetail structureDetail) {
        this.id = structureDetail.id;
        this.gucci = structureDetail.gucci;
        this.gucciAmount = structureDetail.gucciAmount;
    }

    public void updateStep2(StructureDetail structureDetail) {
        this.insideWallYn = structureDetail.insideWallYn;
        this.ceilingYn = structureDetail.ceilingYn;
        this.windowYn = structureDetail.windowYn;
        this.doorYn = structureDetail.doorYn;
        this.canopyYn = structureDetail.canopyYn;
        this.downpipeYn = structureDetail.downpipeYn;
    }

    public void updateStep4(StructureDetail structureDetail) {
        this.insideWallType = structureDetail.insideWallType;
        this.insideWallPaper = structureDetail.insideWallPaper;
        this.insideWallThick = structureDetail.insideWallThick;
        this.outsideWallType = structureDetail.outsideWallType;
        this.outsideWallPaper = structureDetail.outsideWallPaper;
        this.outsideWallThick = structureDetail.outsideWallThick;
        this.roofType = structureDetail.roofType;
        this.roofPaper = structureDetail.roofPaper;
        this.roofThick = structureDetail.roofThick;
        this.ceilingType = structureDetail.ceilingType;
        this.ceilingPaper = structureDetail.ceilingPaper;
        this.ceilingThick = structureDetail.ceilingThick;
    }
}