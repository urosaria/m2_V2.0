package jungjin.board.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jungjin.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "m2_board")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Board implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_board_writer"))
    private User user;

    @NotNull
    @Column(name = "title", length = 500)
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String contents;

    @NotNull
    @Column(name = "status")
    private String status;

    @NotNull
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "read_count")
    private int readcount;

    @NotNull
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_board_master_id"))
    private BoardMaster boardMaster;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board")
    private List<BoardFile> fileList = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "board_id")
    private List<BoardReply> boardReplyList;

    public void update(Board updateBoard) {
        this.title = updateBoard.title;
        this.contents = updateBoard.contents;
    }

    public void insert(Board insertBoard) {
        this.title = insertBoard.title;
        this.contents = insertBoard.contents;
        this.status = "S";
        this.createDate = LocalDateTime.now();
    }
}
