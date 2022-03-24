package jungjin.user.service;

import java.util.HashSet;
import java.util.Set;
import jungjin.user.domain.Role;
import jungjin.user.domain.User;
import jungjin.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findById(username);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if (user == null)
            throw new UsernameNotFoundException(username);
        for (Role role : user.getRoles())
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        return new UserCustom(user.getId(), user.getPassword(), grantedAuthorities, user);
    }
}
