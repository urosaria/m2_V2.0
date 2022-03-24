package jungjin.user.repository;

import jungjin.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(String paramString);

    Page<User> findByNameContaining(String paramString, Pageable paramPageable);

    Page<User> findByIdContaining(String paramString, Pageable paramPageable);

    Page<User> findByEmailContaining(String paramString, Pageable paramPageable);

    Page<User> findByPhoneContaining(String paramString, Pageable paramPageable);
}
