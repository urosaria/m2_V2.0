package jungjin.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "m2_role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String name;
}