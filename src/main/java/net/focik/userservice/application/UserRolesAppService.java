package net.focik.userservice.application;

import lombok.RequiredArgsConstructor;
import net.focik.userservice.domain.AppUser;
import net.focik.userservice.domain.Privilege;
import net.focik.userservice.domain.Role;
import net.focik.userservice.domain.UserFacade;
import net.focik.userservice.domain.exceptions.PrivilegeNotFoundException;
import net.focik.userservice.domain.port.primary.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserRolesAppService implements IGetUserRolesUseCase, IAddRoleToUserUseCase,
        IChangePrivilegeInUserRoleUseCase, IDeleteUsersRoleUseCase{

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
    public List<Privilege> getRoleDetails(Long idUser,Long idRole) {
    return userFacade.getRoleDetails(idUser, idRole);
    }

    @Override
    public void addRoleToUser(Long idUser, Long idRole) {
       userFacade.addRoleToUser(idUser, idRole);
    }

    @Override
    public void changePrivilegesInUserRole(Long idUser, Long idRole, List<String> privilegesToAdd) {
        if(privilegesToAdd == null || privilegesToAdd.isEmpty())
            throw new PrivilegeNotFoundException("Lista przywilejów nie może bć pusta.");

        List<Privilege> privilegeList = new ArrayList<>();
        for (String s:privilegesToAdd) {
            Privilege privilegeByName = userFacade.findPrivilegeByName(s);

            privilegeList.add(privilegeByName);
        }

        userFacade.changePrivilegesInUserRole(idUser, idRole, privilegeList);
    }

    @Override
    public void deleteUsersRoleById(Long id, Long idRole) {
        userFacade.deleteUsersRoleById(id, idRole);
    }
}
