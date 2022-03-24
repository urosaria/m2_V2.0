package jungjin.board.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import jungjin.user.domain.User;

@Entity
@Table(name = "m2_board_reply")
public class BoardReply implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_board_reply_writer"))
    private User user;

    @NotNull
    @Column(name = "title", length = 500)
    private String title;

    @Lob
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
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_board_id"))
    private Board board;

    public void setId(long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public void setReadcount(int readcount) {
        this.readcount = readcount;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public long getId() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContents() {
        return this.contents;
    }

    public String getStatus() {
        return this.status;
    }

    public LocalDateTime getCreateDate() {
        return this.createDate;
    }

    public int getReadcount() {
        return this.readcount;
    }

    public Board getBoard() {
        return this.board;
    }

    public void update(BoardReply upateBoardReply) {
        this.title = upateBoardReply.title;
        this.contents = upateBoardReply.contents;
    }

    public void insert(BoardReply insertBoardReply) {
        this.title = insertBoardReply.title;
        this.contents = insertBoardReply.contents;
        this.status = "S";
        this.createDate = LocalDateTime.now();
    }
}
