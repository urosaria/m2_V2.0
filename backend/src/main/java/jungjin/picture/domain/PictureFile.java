package jungjin.picture.domain;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "m2_picture_file")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class PictureFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @Column(name = "ori_name")
    private String oriName;

    private String ext;

    private String status = "S";

    @NotNull
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "picture_id")
    private Picture picture;
}