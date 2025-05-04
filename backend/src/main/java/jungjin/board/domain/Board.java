package jungjin.board.domain;

import java.io.Serializable;
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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jungjin.user.domain.User;

@Entity
@Table(name = "m2_board")
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

    public void setBoardMaster(BoardMaster boardMaster) {
        this.boardMaster = boardMaster;
    }

    public void setFileList(List<BoardFile> fileList) {
        this.fileList = fileList;
    }

    public void setBoardReplyList(List<BoardReply> boardReplyList) {
        this.boardReplyList = boardReplyList;
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

    public BoardMaster getBoardMaster() {
        return this.boardMaster;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board")
    List<BoardFile> fileList = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "board_id")
    List<BoardReply> boardReplyList;

    public List<BoardFile> getFileList() {
        return this.fileList;
    }

    public List<BoardReply> getBoardReplyList() {
        return this.boardReplyList;
    }

    public void update(Board upateBoard) {
        this.title = upateBoard.title;
        this.contents = upateBoard.contents;
    }

    public void insert(Board insertBoard) {
        this.title = insertBoard.title;
        this.contents = insertBoard.contents;
        this.status = "S";
        this.createDate = LocalDateTime.now();
    }
}
