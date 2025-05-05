package jungjin.user.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private static final Logger log = LoggerFactory.getLogger(SecurityService.class);

    private UserDetailsService userDetailsService;
    private AuthenticationManager authenticationManager;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails)
            return ((UserDetails)userDetails).getUsername();
        return null;
    }

    public void autologin(String username, String password) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        this.authenticationManager.authenticate((Authentication)usernamePasswordAuthenticationToken);
        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication((Authentication)usernamePasswordAuthenticationToken);
            System.out.println("Auto login %s successfully!" + password);
        }
    }

    public String adminLoginCheck(String username, String password) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        System.out.println("userDetails::" + userDetails);
        if (userDetails == null)
            return null;
        boolean passwdCheck = bCryptPasswordEncoder.matches(password, userDetails.getPassword());
        if (!passwdCheck)
            return null;
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        this.authenticationManager.authenticate((Authentication)usernamePasswordAuthenticationToken);
        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication((Authentication)usernamePasswordAuthenticationToken);
            System.out.println("Auto login %s successfully!" + password);
        }
        return "success";
    }
}
