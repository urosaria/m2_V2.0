package jungjin.board.domain;

import jungjin.user.domain.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="board_master")
@Data
@Getter
@Setter
public class BoardMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "reply_yn")
    private String replyYn;

    //S:사용중 D:삭제
    @NotNull
    @Column(name="status")
    String status;

    @Column(name="skin_name")
    String skinName;

    public void update(BoardMaster updateBoardMaster){
        this.name = updateBoardMaster.name;
        this.replyYn = updateBoardMaster.replyYn;
    }
}
