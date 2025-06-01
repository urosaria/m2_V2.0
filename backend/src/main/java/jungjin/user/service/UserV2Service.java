package jungjin.user.service;

import jungjin.admin.dto.StatDTO;
import jungjin.common.exception.BusinessException;
import jungjin.common.exception.NotFoundException;
import jungjin.user.domain.Role;
import jungjin.user.domain.User;
import jungjin.user.dto.UserRequestDTO;
import jungjin.user.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jungjin.user.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserV2Service implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return userRepository.findByLoginId(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    public void insertUser(UserRequestDTO request) {
        if (userRepository.existsByLoginId(request.getId())) {
            throw new BusinessException("DUPLICATE_LOGIN_ID", "Login ID already exists");
        }

        User user = User.builder()
                .loginId(request.getId())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .companyName(request.getCompanyName())
                .companyAddress(request.getCompanyAddress())
                .companyPhone(request.getCompanyPhone())
                .companyWebsite(request.getCompanyWebsite())
                .phone(request.getPhone())
                .agreeYn(request.getAgreeYn())
                .status("S")
                .role(Optional.ofNullable(request.getRole())
                        .map(String::valueOf)
                        .filter(s -> !s.isBlank())
                        .map(Role::valueOf)
                        .orElse(Role.USER))
                .build();

        userRepository.save(user);
    }

    public UserResponseDTO getUserByUserNum(Long userNum) {
        return userRepository.findByNum(userNum)
                .map(UserResponseDTO::fromUser)
                .orElseThrow(() -> new BusinessException("User not found with num: " + userNum));
    }

    public User getUserByUserNumReturnUser(Long userNum) {
        return userRepository.findByNum(userNum)
                .orElseThrow(() -> new NotFoundException("User not found with num: " + userNum));
    }

    public UserResponseDTO updateUser(Long userNum, UserRequestDTO request) {
        User user = userRepository.findByNum(userNum)
                .orElseThrow(() -> new BusinessException("User not found with num: " + userNum));

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setAgreeYn(request.getAgreeYn());
        user.setCompanyName(request.getCompanyName());
        user.setCompanyAddress(request.getCompanyAddress());
        user.setCompanyPhone(request.getCompanyPhone());
        user.setCompanyWebsite(request.getCompanyWebsite());

        return UserResponseDTO.fromUser(userRepository.save(user));
    }

    public void deleteUser(Long userNum) {
        User user = userRepository.findByNum(userNum)
                .orElseThrow(() -> new BusinessException("User not found with num: " + userNum));
        user.setStatus("D");
        userRepository.save(user);
    }

    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserResponseDTO::fromUser);
    }

    public StatDTO getStats() {
        long total = userRepository.count();
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
        long todayCount = userRepository.countByCreateDateBetween(startOfDay, endOfDay);
        return StatDTO.builder().total(total).today(todayCount).build();
    }
}
