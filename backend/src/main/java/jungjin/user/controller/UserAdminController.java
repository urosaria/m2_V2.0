package jungjin.user.controller;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;

import jungjin.user.domain.Role;
import jungjin.user.domain.User;
import jungjin.user.service.RoleService;
import jungjin.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserAdminController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<Page<User>> userList(@RequestParam(value = "page", defaultValue = "1") int page,
                                               @RequestParam(value = "searchCondition", defaultValue = "") String searchCondition,
                                               @RequestParam(value = "searchText", defaultValue = "") String searchText) {
        Page<User> userList = searchCondition.isEmpty()
                ? userService.listUser(page)
                : userService.listSearchTextUser(searchCondition, searchText, page, 10);
        return ResponseEntity.ok(userList);
    }

    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody User userForm) {
        Role defaultRole = Optional.ofNullable(roleService.showRole(1L))
                .orElseThrow(() -> new EntityNotFoundException("Default role not found."));
        userService.saveUser(userForm, defaultRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(userForm);
    }

    @GetMapping("/{num}")
    public ResponseEntity<User> getUser(@PathVariable Long num) {
        User user = userService.showUser(num);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{num}")
    public ResponseEntity<User> updateUser(@PathVariable Long num, @RequestBody User user) {
        userService.updateUser(num, user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{num}")
    public ResponseEntity<String> deleteUser(@PathVariable Long num) {
        User user = userService.deleteUser(num);
        return user != null ? ResponseEntity.ok("success") : ResponseEntity.status(HttpStatus.NOT_FOUND).body("fail");
    }

    @GetMapping("/check-id")
    public ResponseEntity<String> checkUserId(@RequestParam String id) {
        User user = userService.findById(id);
        return user == null ? ResponseEntity.ok("success") : ResponseEntity.status(HttpStatus.CONFLICT).body("fail");
    }
}
