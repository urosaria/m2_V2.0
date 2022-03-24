package jungjin.board.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "m2_board_file")
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

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOriName(String oriName) {
        this.oriName = oriName;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getOriName() {
        return this.oriName;
    }

    public String getExt() {
        return this.ext;
    }

    @Column(name = "status")
    private String status = "S";

    @NotNull
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    public String getStatus() {
        return this.status;
    }

    public LocalDateTime getCreateDate() {
        return this.createDate;
    }

    public Board getBoard() {
        return this.board;
    }
}
