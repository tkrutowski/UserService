package net.focik.userservice.domain;

import lombok.RequiredArgsConstructor;
import net.focik.userservice.domain.exceptions.UserNotFoundException;
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
        AppUser userById = userService.findUserById(idUser);

        AppUser userWithNewRole = roleService.addRoleToUser(userService.findUserById(idUser), idRole);
        userService.saveUser(userWithNewRole);
    }
}
