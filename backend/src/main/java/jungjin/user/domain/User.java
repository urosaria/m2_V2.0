package jungjin.user.domain;

import java.time.LocalDateTime;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "m2_user")
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

    @Column(name = "phone")
    private String phone;

    @Column(name = "agree_yn")
    private String agreeYn;

    @NotNull
    @Column(name = "create_date")
    LocalDateTime createDate;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "m2_user_role", joinColumns = {@JoinColumn(name = "user_num")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    public String toString() {
        return "User(num=" + getNum() + ", id=" + getId() + ", name=" + getName() + ", password=" + getPassword() + ", status=" + getStatus() + ", email=" + getEmail() + ", phone=" + getPhone() + ", agreeYn=" + getAgreeYn() + ", createDate=" + getCreateDate() + ", roles=" + getRoles() + ")";
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAgreeYn(String agreeYn) {
        this.agreeYn = agreeYn;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getNum() {
        return this.num;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public String getStatus() {
        return this.status;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getAgreeYn() {
        return this.agreeYn;
    }

    public LocalDateTime getCreateDate() {
        return this.createDate;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void insert(User insertUser) {
        this.id = insertUser.id;
        this.name = insertUser.name;
        this.password = insertUser.password;
        this.email = insertUser.email;
        this.phone = insertUser.phone;
        this.agreeYn = insertUser.agreeYn;
        LocalDateTime date = LocalDateTime.now();
        this.createDate = date;
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
