package jungjin.board.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "m2_board_master")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "boardMasterSeqGenerator")
    @SequenceGenerator(
            name = "boardMasterSeqGenerator",
            sequenceName = "m2_board_master_seq",
            allocationSize = 1
    )
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "reply_yn")
    private String replyYn;

    @NotNull
    @Column(name = "status")
    private String status;

    @Column(name = "skin_name")
    private String skinName;

}