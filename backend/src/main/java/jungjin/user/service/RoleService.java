package jungjin.user.service;

import jungjin.user.domain.Role;
import jungjin.user.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private RoleRepository roleRepository;

    public Role showRole(Long id) {
        return this.roleRepository.findById(id).orElse(null);
    }
}
