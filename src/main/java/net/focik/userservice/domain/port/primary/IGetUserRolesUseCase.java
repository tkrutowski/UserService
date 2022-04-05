package net.focik.userservice.domain.port.primary;

import net.focik.userservice.domain.Privilege;
import net.focik.userservice.domain.Role;

import java.util.List;

public interface IGetUserRolesUseCase {
    List<Role> getUserRoles(Long idUser);

    List<Role> getUserRoles();


    List<Privilege> getRoleDetails(Long idUser, Long idRole);
}
