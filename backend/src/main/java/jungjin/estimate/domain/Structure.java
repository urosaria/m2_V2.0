package jungjin.estimate.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jungjin.user.domain.User;

@Entity
@Table(name = "m2_est_structure")
public class Structure {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "city_name")
    private String cityName;

    public void setCalculateList(List<Calculate> calculateList) {
        this.calculateList = calculateList;
    }

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

    @Transient
    StructureDetail structureDetail;

    @NotNull
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "status_date")
    private LocalDateTime statusDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "structure")
    @OrderBy("sort ASC")
    List<Calculate> calculateList = new ArrayList<>();

    public List<Calculate> getCalculateList() {
        return this.calculateList;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPlaceName() {
        return this.placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getStructureType() {
        return this.structureType;
    }

    public void setStructureType(String structureType) {
        this.structureType = structureType;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getTrussHeight() {
        return this.trussHeight;
    }

    public void setTrussHeight(int trussHeight) {
        this.trussHeight = trussHeight;
    }

    public int getEavesLength() {
        return this.eavesLength;
    }

    public void setEavesLength(int eavesLength) {
        this.eavesLength = eavesLength;
    }

    public int getRearTrussHeight() {
        return this.rearTrussHeight;
    }

    public void setRearTrussHeight(int rearTrussHeight) {
        this.rearTrussHeight = rearTrussHeight;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public StructureDetail getStructureDetail() {
        return this.structureDetail;
    }

    public void setStructureDetail(StructureDetail structureDetail) {
        this.structureDetail = structureDetail;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getStatusDate() {
        return this.statusDate;
    }

    public void setStatusDate(LocalDateTime statusDate) {
        this.statusDate = statusDate;
    }

    public int getInsideWidth() {
        return this.insideWidth;
    }

    public void setInsideWidth(int insideWidth) {
        this.insideWidth = insideWidth;
    }

    public int getInsideLength() {
        return this.insideLength;
    }

    public void setInsideLength(int insideLength) {
        this.insideLength = insideLength;
    }

    public int getRooftopSideHeight() {
        return this.rooftopSideHeight;
    }

    public void setRooftopSideHeight(int rooftopSideHeight) {
        this.rooftopSideHeight = rooftopSideHeight;
    }

    public int getRooftopWidth() {
        return this.rooftopWidth;
    }

    public void setRooftopWidth(int rooftopWidth) {
        this.rooftopWidth = rooftopWidth;
    }

    public int getRooftopHeight() {
        return this.rooftopHeight;
    }

    public void setRooftopHeight(int rooftopHeight) {
        this.rooftopHeight = rooftopHeight;
    }

    public int getRooftopLength() {
        return this.rooftopLength;
    }

    public void setRooftopLength(int rooftopLength) {
        this.rooftopLength = rooftopLength;
    }
}
