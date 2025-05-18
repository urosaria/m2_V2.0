package jungjin.board.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "m2_board_file")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class BoardFile {

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

    @Column(name = "path")
    private String path;

    @Column(name = "size")
    private Long size;

    @Column(name = "status")
    private String status = "S";

    @CreationTimestamp
    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}