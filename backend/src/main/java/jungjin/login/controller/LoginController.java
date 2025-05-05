package jungjin.login.controller;

import jakarta.servlet.http.HttpServletRequest;
import jungjin.estimate.domain.Structure;
import jungjin.estimate.service.EstimateService;
import jungjin.user.service.SecurityService;
import jungjin.user.service.UserCustom;
import jungjin.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {
    private final EstimateService estimateService;

    @GetMapping("/login")
    public ResponseEntity<String> login(HttpServletRequest request) {
        request.getSession().removeAttribute("prevPage");
        return ResponseEntity.ok("Login endpoint - session cleared");
    }

    @GetMapping("/main")
    public ResponseEntity<Page<Structure>> main(@RequestParam(value = "page", defaultValue = "1") int page) {
        UserCustom principal = (UserCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userNum = principal.getUser().getNum();
        Page<Structure> listEstimate = estimateService.listEstimate(page, 4, userNum);
        return ResponseEntity.ok(listEstimate);
    }

    @GetMapping("/main-new")
    public ResponseEntity<Page<Structure>> mainNew(@RequestParam(value = "page", defaultValue = "1") int page) {
        UserCustom principal = (UserCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userNum = principal.getUser().getNum();
        Page<Structure> listEstimate = estimateService.listEstimate(page, 4, userNum);
        return ResponseEntity.ok(listEstimate);
    }

    @GetMapping("/original-img")
    public ResponseEntity<String> imageStub() {
        return ResponseEntity.ok("Image view placeholder endpoint");
    }
}