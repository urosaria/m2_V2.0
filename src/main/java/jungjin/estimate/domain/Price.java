package jungjin.estimate.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "m2_est_price")
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

    public void setId(int id) {
        this.id = id;
    }

    public void setGubun(String gubun) {
        this.gubun = gubun;
    }

    public void setSubGubun(String subGubun) {
        this.subGubun = subGubun;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    public void setGapPrice(int gapPrice) {
        this.gapPrice = gapPrice;
    }

    public void setMaxThickPrice(int maxThickPrice) {
        this.maxThickPrice = maxThickPrice;
    }

    public void setStandardPrice(int standardPrice) {
        this.standardPrice = standardPrice;
    }

    public void setEPrice(int ePrice) {
        this.ePrice = ePrice;
    }

    public int getId() {
        return this.id;
    }

    public String getGubun() {
        return this.gubun;
    }

    public String getSubGubun() {
        return this.subGubun;
    }

    public String getType() {
        return this.type;
    }

    public String getSubType() {
        return this.subType;
    }

    public int getStartPrice() {
        return this.startPrice;
    }

    public int getGapPrice() {
        return this.gapPrice;
    }

    @Column(name = "max_thick_price")
    private int maxThickPrice = 0;

    public int getMaxThickPrice() {
        return this.maxThickPrice;
    }

    @Column(name = "standard_price")
    private int standardPrice = 0;

    public int getStandardPrice() {
        return this.standardPrice;
    }

    @Column(name = "e_price", columnDefinition = "INT(11) default '0'")
    private int ePrice = 0;

    public int getEPrice() {
        return this.ePrice;
    }
}
