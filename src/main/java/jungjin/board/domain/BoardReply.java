package jungjin.board.domain;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import jungjin.user.domain.User;

@Getter
@Entity
@Data
@Table(name="board_reply")
public class BoardReply implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name="fk_board_reply_writer"))
    private User user;

    @NotNull
    @Column(name="title", length=500)
    private String title;

    @Lob
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

    @NotNull
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name="fk_board_id"))
    private Board board;

    public void update(BoardReply upateBoardReply){
        this.title = upateBoardReply.title;
        this.contents = upateBoardReply.contents;
    }

    public void insert(BoardReply insertBoardReply){
        this.title = insertBoardReply.title;
        this.contents = insertBoardReply.contents;
        this.status="S";
        this.createDate = LocalDateTime.now();
    }

}
