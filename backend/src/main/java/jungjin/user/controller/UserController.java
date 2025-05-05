package jungjin.user.controller;

import jungjin.user.domain.Role;
import jungjin.user.domain.User;
import jungjin.user.service.RoleService;
import jungjin.user.service.UserCustom;
import jungjin.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/mypage")
    public ResponseEntity<User> myPage() {
        Long userNum = ((UserCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getNum();
        User userInfo = userService.showUser(userNum);
        return ResponseEntity.ok(userInfo);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User userForm) {
        Role userRole = roleService.showRole(2L);
        User result = userService.saveUser(userForm, userRole);
        return result != null ? ResponseEntity.status(HttpStatus.CREATED).body("success") : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
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
