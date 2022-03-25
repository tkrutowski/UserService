package net.focik.userservice.application;

import lombok.RequiredArgsConstructor;
import net.focik.userservice.domain.AppUser;
import net.focik.userservice.domain.UserFacade;
import net.focik.userservice.domain.port.primary.IGetUserUseCase;
import net.focik.userservice.domain.port.primary.IAddNewUserUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserAppService implements IGetUserUseCase, IAddNewUserUseCase {

    private final UserFacade userFacade;

    @Override
    public AppUser findUserByUsername(String username) {
        return userFacade.findUserByUsername(username);
    }

    @Override
    public List<AppUser> getAllUsers() {
        return null;
    }

    @Override
    public AppUser addNewUser(String firstName, String lastName, String username, String password, String email, boolean enabled,
                              boolean isNotLocked) {
        return userFacade.registerUser(firstName, lastName, username, password, email, enabled, isNotLocked);
    }
}
