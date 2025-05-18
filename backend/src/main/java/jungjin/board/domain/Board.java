package jungjin.board.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jungjin.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "m2_board")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "boardSeqGenerator")
    @SequenceGenerator(
            name = "boardSeqGenerator",
            sequenceName = "m2_board_seq",
            allocationSize = 1
    )
    private long id;

    @NotNull
    @Column(name = "title", length = 500)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String contents;

    @CreationTimestamp
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "read_count")
    private int readcount;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_num")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "board_master_id")
    private BoardMaster boardMaster;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board")
    private List<BoardFile> files = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardReply> boardReplyList;

}
