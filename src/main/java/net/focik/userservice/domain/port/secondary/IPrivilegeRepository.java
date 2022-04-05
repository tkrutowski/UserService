package net.focik.userservice.domain.port.secondary;

import net.focik.userservice.domain.Privilege;
import net.focik.userservice.domain.Role;

import java.util.List;

public interface IPrivilegeRepository {

//    Role addRole(Role role);
//    List<Role> getAllRoles();
     Privilege getPrivilegeByName(String name);
}
