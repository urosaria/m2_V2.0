package jungjin.user.domain;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
@Getter
@Setter
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

    private String status;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name ="phone")
    private String phone;

    @Column(name ="agree_yn")
    private String agreeYn;

    @NotNull
    @Column(name="create_date")
    LocalDateTime createDate;

    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_num"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))

    private Set<Role> roles;

    public void insert(User insertUser){
        this.id =insertUser.id;
        this.name =insertUser.name;
        this.password = insertUser.password;
        this.email = insertUser.email;
        this.phone = insertUser.phone;
        this.agreeYn = insertUser.agreeYn;
        LocalDateTime date = LocalDateTime.now();
        this.createDate = date;
        this.roles = insertUser.roles;
        this.status = "S";
    }

    public void update(User updateUser){
        this.email = updateUser.email;
        this.phone = updateUser.phone;
        this.agreeYn = updateUser.agreeYn;
        this.password = updateUser.password;
    }

    public void delete(User deleteUser){
        this.status = "D";
    }
}