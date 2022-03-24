package jungjin.user.service;

import jungjin.user.domain.Role;
import jungjin.user.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role showRole(Long id) {
        return (Role)this.roleRepository.findOne(id);
    }
}
