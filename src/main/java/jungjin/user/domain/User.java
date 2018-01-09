package jungjin.user.domain;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@Entity
@Data
@Table(name="user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long num;

    @NotNull
    @Column(name="id")
    String id;

    @NotNull
    @Column(name="passwd")
    String passwd;

    @NotNull
    @Column(name="name")
    String name;

    @NotNull
    @Column(name="phone")
    String phone;

    @NotNull
    @Column(name="email")
    String email;

    @NotNull
    @Column(name="status")
    String status="S";

    @NotNull
    @Column(name="agree_yn")
    String agreeYn;

    @NotNull
    @Column(name="create_date")
    LocalDateTime createDate;

    public void update(User updateUser){
        this.email = updateUser.email;
        this.phone = updateUser.phone;
        this.agreeYn = updateUser.agreeYn;
        this.passwd = updateUser.passwd;
    }

    public void delete(User deleteUser){
        this.status = "D";
    }

}
