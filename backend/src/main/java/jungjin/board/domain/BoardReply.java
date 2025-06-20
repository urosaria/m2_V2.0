package jungjin.board.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jungjin.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "m2_board_reply")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
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

    @Column(columnDefinition = "TEXT")
    private String contents;

    @NotNull
    @Column(name = "status")
    private String status;

    @CreationTimestamp
    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;

    @Column(name = "read_count")
    private int readcount;

    @NotNull
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_board_id"))
    private Board board;

}
