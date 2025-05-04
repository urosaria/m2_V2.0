package jungjin.picture.domain;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "m2_picture_admin_file")
public class PictureAdminFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "ori_name")
    private String oriName;

    @Column(name = "ext")
    private String ext;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOriName(String oriName) {
        this.oriName = oriName;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getOriName() {
        return this.oriName;
    }

    public String getExt() {
        return this.ext;
    }

    @Column(name = "status")
    private String status = "S";

    public String getStatus() {
        return this.status;
    }

    @Column(name = "path")
    private String path = "";

    @NotNull
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "picture_id")
    private Picture picture;

    public String getPath() {
        return this.path;
    }

    public LocalDateTime getCreateDate() {
        return this.createDate;
    }

    public Picture getPicture() {
        return this.picture;
    }
}
