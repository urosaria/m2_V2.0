package jungjin.board.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jungjin.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "m2_board")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(name = "title", length = 500)
    private String title;

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
    @JoinColumn(name = "user_num")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "board_master_id")
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
