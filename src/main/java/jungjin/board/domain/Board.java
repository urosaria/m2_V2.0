package jungjin.board.domain;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Entity
@Data
@Table(name="board")
public class Board implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(name="user_id")
    private long userId;

    @NotNull
    @Column(name="title", length=500)
    private String title;

    @Column(name="contents")
    private String contents;

    //S:사용중 D:삭제
    @NotNull
    @Column(name="status")
    private String status;

    @NotNull
    @Column(name="create_date")
    private LocalDateTime createDate;

    @Column(name="read_count")
    private int readcount;

    @Column(name="reply_id")
    private long replyId;

    @Column(name="reply_yn")
    private String replyYn;

    @ManyToOne
    @JoinColumn(name="board_master_id")
    private BoardMaster boardMaster;

}
