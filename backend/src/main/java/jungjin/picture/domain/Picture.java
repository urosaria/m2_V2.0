package jungjin.picture.domain;

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
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jungjin.user.domain.User;

@Entity
@Table(name = "m2_picture")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "etc")
    private String etc;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public void setFileList(List<PictureFile> fileList) {
        this.fileList = fileList;
    }

    public void setAdminFileList(List<PictureAdminFile> adminFileList) {
        this.adminFileList = adminFileList;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEtc() {
        return this.etc;
    }

    @Column(name = "status")
    private String status = "S1";

    @NotNull
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_est_picture_writer"))
    private User user;

    @NotNull
    @Column(name = "create_date")
    private LocalDateTime createDate;

    public String getStatus() {
        return this.status;
    }

    public User getUser() {
        return this.user;
    }

    public LocalDateTime getCreateDate() {
        return this.createDate;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "picture")
    List<PictureFile> fileList = new ArrayList<>();

    public List<PictureFile> getFileList() {
        return this.fileList;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "picture")
    List<PictureAdminFile> adminFileList = new ArrayList<>();

    public List<PictureAdminFile> getAdminFileList() {
        return this.adminFileList;
    }

    public void update(Picture pictureUpdate) {
        this.id = pictureUpdate.id;
        this.name = pictureUpdate.name;
        this.etc = pictureUpdate.etc;
    }

    public void updateStatus(Picture pictureUpdate) {
        this.id = pictureUpdate.id;
        this.status = pictureUpdate.status;
    }
}
