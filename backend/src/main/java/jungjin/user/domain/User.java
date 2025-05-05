package jungjin.user.domain;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "m2_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "num")
    private Long num;

    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "agree_yn")
    private String agreeYn;

    @NotNull
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "m2_user_role",
            joinColumns = @JoinColumn(name = "user_num"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @Override
    public String toString() {
        return String.format(
                "User(num=%s, id=%s, name=%s, password=%s, status=%s, email=%s, phone=%s, agreeYn=%s, createDate=%s, roles=%s)",
                num, id, name, password, status, email, phone, agreeYn, createDate, roles
        );
    }

    public void insert(User insertUser) {
        this.id = insertUser.id;
        this.name = insertUser.name;
        this.password = insertUser.password;
        this.email = insertUser.email;
        this.phone = insertUser.phone;
        this.agreeYn = insertUser.agreeYn;
        this.createDate = LocalDateTime.now();
        this.roles = insertUser.roles;
        this.status = "S";
    }

    public void update(User updateUser) {
        this.name = updateUser.name;
        this.email = updateUser.email;
        this.phone = updateUser.phone;
        this.agreeYn = updateUser.agreeYn;
        this.password = updateUser.password;
    }

    public void delete(User deleteUser) {
        this.status = "D";
    }
}