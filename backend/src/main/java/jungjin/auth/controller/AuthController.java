package jungjin.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jungjin.auth.dto.LoginRequestDTO;
import jungjin.auth.dto.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
//    private final AuthService authService;
//
//    @PostMapping("/login")
//    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
//        LoginResponseDTO response = authService.login(request);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @PostMapping("/refresh")
//    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
//        String authHeader = request.getHeader("Authorization");
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            return ResponseEntity.badRequest().body("Missing Authorization header");
//        }
//
//        String expiredToken = authHeader.substring(7);
//        String username;
//        try {
//            username = jwtUtils.extractUsername(expiredToken);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
//        }
//
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        String newToken = jwtUtils.generateToken(userDetails); // create new token
//
//        return ResponseEntity.ok(Map.of("token", newToken));
//    }
}
