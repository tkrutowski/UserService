package net.focik.userservice.domain.port.secondary;

import net.focik.userservice.domain.Role;

import java.util.List;

public interface IRoleRepository {

    Role addRole(Role role);
    List<Role> getAllRoles();
    Role getRoleById(Long id);
}
