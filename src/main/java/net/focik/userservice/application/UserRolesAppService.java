package net.focik.userservice.application;

import lombok.RequiredArgsConstructor;
import net.focik.userservice.domain.AppUser;
import net.focik.userservice.domain.Privilege;
import net.focik.userservice.domain.Role;
import net.focik.userservice.domain.UserFacade;
import net.focik.userservice.domain.exceptions.PrivilegeNotFoundException;
import net.focik.userservice.domain.exceptions.RoleNotFoundException;
import net.focik.userservice.domain.port.primary.*;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class UserRolesAppService implements IGetUserRolesUseCase, IAddRoleToUserUseCase,
        IChangePrivilegeInUserRoleUseCase, IDeleteUsersRoleUseCase{

    private final UserFacade userFacade;

    @Override
    public List<Role> getUserRoles(Long idUser) {
        List<Role> rolesList = new ArrayList<>();
        AppUser user = userFacade.findUserById(idUser);
        user.getPrivileges().forEach(privilege -> rolesList.add(privilege.getRole()));

        return rolesList;
    }

    @Override
    public List<Role> getRoles() {
        return userFacade.getAllRoles();
    }
//
//    @Override
//    public Privilege getRoleDetailsDto(Long idUser, Long idRole) {
//        //pobiera nazwe roli
////        List<Role> userRoles = getUserRoles(idUser);
//        AppUser user = userFacade.findUserById(idUser);
//        List<Privilege> privileges = user.getPrivileges();
//
//
////        Optional<Role> optionalRole =
//        Optional<Privilege> optionalPrivilege = privileges.stream()
//                .filter(privilege -> privilege.getRole().getId().equals(idRole))
//                .findFirst();
//
//        if(optionalPrivilege.isEmpty())
//            throw new RoleNotFoundException("Role id: "+idRole+" not found for user: "+idUser);
////        String roleName = optionalRole.get().getName();
////        Map<String, String> map = new HashMap<>();
////        map.put("read", "NULL");
////        map.put("write", "NULL");
////        map.put("delete", "NULL");
////        for (Privilege p : optionalRole.get().getPrivileges()) {
////            String substring = p.getName().substring("ROLE_".length() );
////            String roleValue = p.getName().substring(roleName.length() - "ROLE_".length() +1);//+_
////
////            if(roleValue.contains("READ"))
////                map.replace("read", roleValue);
////
////            if(roleValue.contains("WRITE"))
////                map.replace("write", roleValue);
////
////            if(roleValue.contains("DELETE"))
////                map.replace("delete", roleValue);
////
////        }
////        List<PrivilegeDto> dtoList = map.entrySet().stream()
////                .map(s -> new PrivilegeDto(s.getKey(), s.getValue()))
////                .collect(Collectors.toList());
////        return dtoList;
//    return optionalPrivilege.get();
//    }

    @Override
    public Privilege getRoleDetails(Long idUser, Long idRole) {
        return userFacade.getRoleDetails(idUser, idRole);
    }

    @Override
    public void addRoleToUser(Long idUser, Long idRole) {
       userFacade.addRoleToUser(idUser, idRole);
    }

    @Override
    public void changePrivilegesInUserRole(Long idUser, Long idRole, Map<String, String> privilegesToAdd) {
        if(privilegesToAdd == null || privilegesToAdd.isEmpty())
            throw new PrivilegeNotFoundException("Lista przywilejów nie może bć pusta.");
//
//        List<Privilege> privilegeList = new ArrayList<>();
//        for (String s:privilegesToAdd) {
//            Privilege privilegeByName = userFacade.findPrivilegeByName(s);
//
//            privilegeList.add(privilegeByName);
//        }
//
        userFacade.changePrivilegesInUserRole(idUser, idRole, privilegesToAdd);
    }

    @Override
    public void deleteUsersRoleById(Long id, Long idRole) {
        userFacade.deleteUsersRoleById(id, idRole);
    }
}
