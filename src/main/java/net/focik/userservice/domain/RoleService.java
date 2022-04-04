package net.focik.userservice.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.focik.userservice.domain.exceptions.RoleNotFoundException;
import net.focik.userservice.domain.exceptions.UserNotFoundException;
import net.focik.userservice.domain.port.secondary.IRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {
    private final IRoleRepository roleRepository;

    public List<Role> getAllRoles() {

        return roleRepository.getAllRoles();
    }

    public AppUser addRoleToUser(AppUser user, Long idRole) {
        if (user == null)
            throw new UserNotFoundException("User not found");

        Role roleById = roleRepository.getRoleById(idRole);

        if(roleById == null)
            throw new RoleNotFoundException("Role not found by id: "+ idRole);

        user.addRole(roleById);

        return user;
    }
}
