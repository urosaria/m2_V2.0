package jungjin.user.controller;

import jakarta.validation.Valid;
import jungjin.user.dto.UserCreateRequestDTO;
import jungjin.user.dto.UserUpdateRequestDTO;
import jungjin.user.dto.UserResponseDTO;
import jungjin.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> register(@Valid @RequestBody UserCreateRequestDTO request) {
        userService.insertUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{userNum}")
    public ResponseEntity<UserResponseDTO> getUserByUserNum(@PathVariable Long userNum) {
        return ResponseEntity.ok(userService.getUserByUserNum(userNum));
    }

    @PutMapping("/{userNum}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long userNum,
            @Valid @RequestBody UserUpdateRequestDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(userNum, userDTO));
    }

    @DeleteMapping("/{userNum}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userNum) {
        userService.deleteUser(userNum);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list")
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }
}
