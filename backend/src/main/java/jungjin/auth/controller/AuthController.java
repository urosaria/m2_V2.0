package jungjin.auth.controller;

import jakarta.validation.Valid;
import jungjin.auth.dto.LoginRequestDTO;
import jungjin.auth.dto.LoginResponseDTO;
import jungjin.auth.security.JwtTokenProvider;
import jungjin.common.exception.BusinessException;
import jungjin.user.domain.User;
import jungjin.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginDto) {
        User user = userRepository.findByLoginId(loginDto.getId())
                .orElseThrow(() -> new BusinessException("Login ID or password incorrect"));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new BusinessException("Login ID or password incorrect");
        }

        String token = jwtTokenProvider.createToken(
                user.getLoginId(),
                List.of(user.getRole().name())
        );

        Instant expiresAt = Instant.now().plusMillis(jwtTokenProvider.getExpirationTime());
        LoginResponseDTO dto = LoginResponseDTO.builder()
                .id(user.getLoginId())
                .role(user.getRole().name())
                .name(user.getName())
                .access_token(token)
                .token_type("Bearer")
                .expires_in(jwtTokenProvider.getExpirationTime() / 1000)
                .expires_at(expiresAt.toString())
                .build();

        return ResponseEntity.ok(dto);
    }
}
