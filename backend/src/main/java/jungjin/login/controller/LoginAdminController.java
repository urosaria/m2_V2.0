package jungjin.login.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jungjin.user.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class LoginAdminController {

    private final SecurityService securityService;

    @PostMapping("/login")
    public ResponseEntity<String> adminLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletRequest request) {

        String autologin = securityService.adminLoginCheck(username, password);

        if (autologin != null) {
            request.getSession().setAttribute("prevPage", "/admin/user/list");
            return ResponseEntity.ok("Login successful");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/login-error")
    public ResponseEntity<String> loginError(@RequestParam(required = false) String username) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Your username and password is invalid.");
    }
}