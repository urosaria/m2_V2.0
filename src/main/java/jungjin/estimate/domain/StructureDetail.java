package jungjin.estimate.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "m2_est_structure_detail")
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

    @Column(name = "inside_wall_type")
    private String insideWallType;

    @Column(name = "inside_wall_paper")
    private String insideWallPaper;

    @Column(name = "inside_wall_thick")
    private int insideWallThick;

    @Column(name = "outside_wall_type")
    private String outsideWallType;

    @Column(name = "outside_wall_paper")
    private String outsideWallPaper;

    @Column(name = "outside_wall_thick")
    private int outsideWallThick;

    @Column(name = "roof_type")
    private String roofType;

    @Column(name = "roof_paper")
    private String roofPaper;

    @Column(name = "roof_thick")
    private int roofThick;

    @Column(name = "ceiling_type")
    private String ceilingType;

    @Column(name = "ceiling_paper")
    private String ceilingPaper;

    @Column(name = "ceiling_thick")
    private int ceilingThick;

    @NotNull
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_est_structure_detail"))
    private Structure structure;

    @NotNull
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "gucci")
    private int gucci;

    @Column(name = "gucci_inside", columnDefinition = "Int(11) default '0'")
    private int gucciInside = 0;

    @Column(name = "gucci_inside_amount", columnDefinition = "Int(11) default '0'")
    private int gucciInsideAmount = 0;

    @Column(name = "gucci_amount")
    private int gucciAmount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "structureDetail")
    List<Canopy> canopyList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "structureDetail")
    List<Ceiling> ceilingList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "structureDetail")
    List<Door> doorList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "structureDetail")
    List<Downpipe> downpipeList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "structureDetail")
    List<InsideWall> insideWallList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "structureDetail")
    List<Window> windowList = new ArrayList<>();

    public void insert(StructureDetail structureDetail) {
        this.createDate = LocalDateTime.now();
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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInsideWallYn() {
        return this.insideWallYn;
    }

    public void setInsideWallYn(String insideWallYn) {
        this.insideWallYn = insideWallYn;
    }

    public String getCeilingYn() {
        return this.ceilingYn;
    }

    public void setCeilingYn(String ceilingYn) {
        this.ceilingYn = ceilingYn;
    }

    public String getWindowYn() {
        return this.windowYn;
    }

    public void setWindowYn(String windowYn) {
        this.windowYn = windowYn;
    }

    public String getDoorYn() {
        return this.doorYn;
    }

    public void setDoorYn(String doorYn) {
        this.doorYn = doorYn;
    }

    public String getCanopyYn() {
        return this.canopyYn;
    }

    public void setCanopyYn(String canopyYn) {
        this.canopyYn = canopyYn;
    }

    public String getDownpipeYn() {
        return this.downpipeYn;
    }

    public void setDownpipeYn(String downpipeYn) {
        this.downpipeYn = downpipeYn;
    }

    public String getInsideWallType() {
        return this.insideWallType;
    }

    public void setInsideWallType(String insideWallType) {
        this.insideWallType = insideWallType;
    }

    public String getInsideWallPaper() {
        return this.insideWallPaper;
    }

    public void setInsideWallPaper(String insideWallPaper) {
        this.insideWallPaper = insideWallPaper;
    }

    public int getInsideWallThick() {
        return this.insideWallThick;
    }

    public void setInsideWallThick(int insideWallThick) {
        this.insideWallThick = insideWallThick;
    }

    public String getOutsideWallType() {
        return this.outsideWallType;
    }

    public void setOutsideWallType(String outsideWallType) {
        this.outsideWallType = outsideWallType;
    }

    public String getOutsideWallPaper() {
        return this.outsideWallPaper;
    }

    public void setOutsideWallPaper(String outsideWallPaper) {
        this.outsideWallPaper = outsideWallPaper;
    }

    public int getOutsideWallThick() {
        return this.outsideWallThick;
    }

    public void setOutsideWallThick(int outsideWallThick) {
        this.outsideWallThick = outsideWallThick;
    }

    public String getRoofType() {
        return this.roofType;
    }

    public void setRoofType(String roofType) {
        this.roofType = roofType;
    }

    public String getRoofPaper() {
        return this.roofPaper;
    }

    public void setRoofPaper(String roofPaper) {
        this.roofPaper = roofPaper;
    }

    public int getRoofThick() {
        return this.roofThick;
    }

    public void setRoofThick(int roofThick) {
        this.roofThick = roofThick;
    }

    public String getCeilingType() {
        return this.ceilingType;
    }

    public void setCeilingType(String ceilingType) {
        this.ceilingType = ceilingType;
    }

    public String getCeilingPaper() {
        return this.ceilingPaper;
    }

    public void setCeilingPaper(String ceilingPaper) {
        this.ceilingPaper = ceilingPaper;
    }

    public int getCeilingThick() {
        return this.ceilingThick;
    }

    public void setCeilingThick(int ceilingThick) {
        this.ceilingThick = ceilingThick;
    }

    public Structure getStructure() {
        return this.structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public LocalDateTime getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public int getGucci() {
        return this.gucci;
    }

    public void setGucci(int gucci) {
        this.gucci = gucci;
    }

    public int getGucciAmount() {
        return this.gucciAmount;
    }

    public void setGucciAmount(int gucciAmount) {
        this.gucciAmount = gucciAmount;
    }

    public List<Canopy> getCanopyList() {
        return this.canopyList;
    }

    public void setCanopyList(List<Canopy> canopyList) {
        this.canopyList = canopyList;
    }

    public List<Ceiling> getCeilingList() {
        return this.ceilingList;
    }

    public void setCeilingList(List<Ceiling> ceilingList) {
        this.ceilingList = ceilingList;
    }

    public List<Door> getDoorList() {
        return this.doorList;
    }

    public void setDoorList(List<Door> doorList) {
        this.doorList = doorList;
    }

    public List<Downpipe> getDownpipeList() {
        return this.downpipeList;
    }

    public void setDownpipeList(List<Downpipe> downpipeList) {
        this.downpipeList = downpipeList;
    }

    public List<InsideWall> getInsideWallList() {
        return this.insideWallList;
    }

    public void setInsideWallList(List<InsideWall> insideWallList) {
        this.insideWallList = insideWallList;
    }

    public List<Window> getWindowList() {
        return this.windowList;
    }

    public void setWindowList(List<Window> windowList) {
        this.windowList = windowList;
    }

    public int getGucciInside() {
        return this.gucciInside;
    }

    public void setGucciInside(int gucciInside) {
        this.gucciInside = gucciInside;
    }

    public int getGucciInsideAmount() {
        return this.gucciInsideAmount;
    }

    public void setGucciInsideAmount(int gucciInsideAmount) {
        this.gucciInsideAmount = gucciInsideAmount;
    }
}
