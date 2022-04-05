package net.focik.userservice.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final IUserService userService;
    private final RoleService roleService;

    public AppUser registerUser(String firstName, String lastName, String username, String password,
                                String email, boolean enabled, boolean isNotLocked) {
        return userService.addNewUser(firstName, lastName, username, password, email, enabled, isNotLocked);
    }

    public AppUser findUserByUsername(String username) {
         return userService.findUserByUsername(username);
    }

    public AppUser updateUser(Long id, String firstName, String lastName, String username, String email) {
        return userService.updateUser(id, firstName, lastName, username, email);
    }

    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }

    public void changePassword(Long id, String oldPassword, String newPassword) {
        userService.changePassword(id, oldPassword, newPassword);
    }

    public AppUser findUserById(Long id) {
        return userService.findUserById(id);
    }

    public List<AppUser> getAllUsers() {
        return userService.getUsers();
    }

    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    public void addRoleToUser(Long idUser, Long idRole) {
        AppUser userWithNewRole = roleService.addRoleToUser(userService.findUserById(idUser), idRole);
        userService.saveUser(userWithNewRole);
    }

    public Privilege findPrivilegeByName(String name){
        return roleService.findPrivilegeByName(name);
    }

    public void changePrivilegesInUserRole(Long idUser, Long idRole, List<Privilege> privilegeList) {
        AppUser userById = userService.findUserById(idUser);
        boolean result = roleService.changePrivilegesInUserRole(userById, idRole, privilegeList);

        if(result)
            userService.saveUser(userById);
    }

    public void deleteUsersRoleById(Long idUser, Long idRole) {
        AppUser userById = userService.findUserById(idUser);
        boolean result = roleService.deleteRoleFromUser(userById, idRole);

        if(result)
            userService.saveUser(userById);
    }

    public List<Privilege> getRoleDetails(Long idUser, Long idRole) {
        AppUser userById = userService.findUserById(idUser);
        return roleService.getRoleDetails(userById, idRole);
    }
}
