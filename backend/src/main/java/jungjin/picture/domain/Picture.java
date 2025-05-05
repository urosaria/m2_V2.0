package jungjin.picture.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jungjin.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "m2_picture")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String etc;

    @Column(name = "status")
    private String status = "S1";

    @NotNull
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_est_picture_writer"))
    private User user;

    @NotNull
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "picture")
    private List<PictureFile> fileList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "picture")
    private List<PictureAdminFile> adminFileList = new ArrayList<>();

    public void update(Picture pictureUpdate) {
        this.name = pictureUpdate.name;
        this.etc = pictureUpdate.etc;
    }

    public void updateStatus(Picture pictureUpdate) {
        this.status = pictureUpdate.status;
    }
}