package jungjin.board.domain;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

@Getter
@Entity
@Data
@Table(name="board")
public class Board implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(name="name")
    String name;

    //S:사용중 D:삭제
    @NotNull
    @Column(name="status")
    String status;

    @Column(name="etc")
    String etc;
}
