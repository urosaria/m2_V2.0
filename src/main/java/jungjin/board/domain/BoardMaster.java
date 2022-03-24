package jungjin.board.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "m2_board_master")
public class BoardMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "reply_yn")
    private String replyYn;

    @NotNull
    @Column(name = "status")
    String status;

    @Column(name = "skin_name")
    String skinName;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReplyYn(String replyYn) {
        this.replyYn = replyYn;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSkinName(String skinName) {
        this.skinName = skinName;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getReplyYn() {
        return this.replyYn;
    }

    public String getStatus() {
        return this.status;
    }

    public String getSkinName() {
        return this.skinName;
    }

    public void update(BoardMaster updateBoardMaster) {
        this.name = updateBoardMaster.name;
        this.replyYn = updateBoardMaster.replyYn;
    }
}
