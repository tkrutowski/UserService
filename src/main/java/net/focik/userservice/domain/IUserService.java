package net.focik.userservice.domain;

import java.util.List;

public interface IUserService {

    AppUser addNewUser(String firstName, String lastName, String username, String password, String email,
                       boolean enabled, boolean isNotLocked);
    AppUser updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String newEmail);
    AppUser findUserByUsername(String username);
    AppUser findUserByEmail(String email);
    List<AppUser> getUsers();

//    void deleteUser(Long id);
//    void resetPassword(String email);
//    void changePassword(String currentUser, String currentPassword, String newPassword);
//    void updateRole(String currentUser, Collection<Role> roles);


}
