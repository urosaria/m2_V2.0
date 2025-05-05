package jungjin.board.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "m2_board_master")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
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
    private String status;

    @Column(name = "skin_name")
    private String skinName;

    public void update(BoardMaster updateBoardMaster) {
        this.name = updateBoardMaster.name;
        this.replyYn = updateBoardMaster.replyYn;
    }
}