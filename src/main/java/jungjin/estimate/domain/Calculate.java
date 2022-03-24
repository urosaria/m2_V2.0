package jungjin.estimate.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "m2_est_calculate")
public class Calculate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @NotNull
    @ManyToOne
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

    public void setId(long id) {
        this.id = id;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setUPrice(int uPrice) {
        this.uPrice = uPrice;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEPrice(int ePrice) {
        this.ePrice = ePrice;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public long getId() {
        return this.id;
    }

    public Structure getStructure() {
        return this.structure;
    }

    public String getName() {
        return this.name;
    }

    public String getStandard() {
        return this.standard;
    }

    public String getUnit() {
        return this.unit;
    }

    public int getAmount() {
        return this.amount;
    }

    public int getUPrice() {
        return this.uPrice;
    }

    @Column(name = "type")
    private String type = "P";

    @Column(name = "e_price")
    private int ePrice;

    @Column(name = "total")
    private long total;

    @Column(name = "c_sort", columnDefinition = "INT default '0'")
    private int sort;

    public String getType() {
        return this.type;
    }

    public int getEPrice() {
        return this.ePrice;
    }

    public long getTotal() {
        return this.total;
    }

    public int getSort() {
        return this.sort;
    }
}
