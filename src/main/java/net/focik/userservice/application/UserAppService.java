package net.focik.userservice.application;

import lombok.RequiredArgsConstructor;
import net.focik.userservice.domain.AppUser;
import net.focik.userservice.domain.UserFacade;
import net.focik.userservice.domain.port.primary.*;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserAppService implements IGetUserUseCase, IAddNewUserUseCase, IUpdateUserUseCase,
        IDeleteUserUseCase, IChangePasswordUseCase {

    private final UserFacade userFacade;

    @Override
    public AppUser findUserByUsername(String username) {
        return userFacade.findUserByUsername(username);
    }

    @Override
    public AppUser findUserById(Long id) {
        return userFacade.findUserById(id);
    }

    @Override
    public List<AppUser> getAllUsers() {
        return userFacade.getAllUsers();
    }

    @Override
    public AppUser addNewUser(String firstName, String lastName, String username, String password, String email, boolean enabled,
                              boolean isNotLocked) {
        return userFacade.registerUser(firstName, lastName, username, password, email, enabled, isNotLocked);
    }

    @Override
    public AppUser updateUser(Long id, String firstName, String lastName, String username, String email) {
        return userFacade.updateUser(id, firstName, lastName, username, email);
    }

    @Override
    public void deleteUserById(Long id) {
        userFacade.deleteUser(id);
    }

    @Override
    public void changePassword(Long id, String oldPassword, String newPassword) {
        userFacade.changePassword(id, oldPassword, newPassword);
    }
}
