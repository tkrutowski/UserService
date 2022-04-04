package net.focik.userservice.application;

import lombok.RequiredArgsConstructor;
import net.focik.userservice.domain.AppUser;
import net.focik.userservice.domain.Role;
import net.focik.userservice.domain.UserFacade;
import net.focik.userservice.domain.port.primary.*;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserRolesAppService implements IGetUserRolesUseCase, IAddRoleToUserUseCase{

    private final UserFacade userFacade;

    @Override
    public List<Role> getUserRoles(Long idUser) {
        List<Role> rolesList = null;
        AppUser user = userFacade.findUserById(idUser);
        rolesList = (List<Role>) user.getRoles();

        return rolesList;
    }

    @Override
    public List<Role> getUserRoles() {
        return userFacade.getAllRoles();
    }

    @Override
    public void addRoleToUser(Long idUser, Long idRole) {
       userFacade.addRoleToUser(idUser, idRole);
    }
}
