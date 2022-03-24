package net.focik.userservice.application;

import lombok.RequiredArgsConstructor;
import net.focik.userservice.domain.AppUser;
import net.focik.userservice.domain.UserFacade;
import net.focik.userservice.domain.port.primary.IGetUserUseCase;
import net.focik.userservice.domain.port.primary.IRegisterUserUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserAppService implements IGetUserUseCase, IRegisterUserUseCase {

    private final UserFacade userFacade;

    @Override
    public AppUser getUserByName(String name) {
        return null;// userFacade.getUser(name);
    }

    @Override
    public List<AppUser> getAllUsers() {
        return null;
    }

    @Override
    public AppUser registerUser(String firstName, String lastName, String username, String email) {
        return userFacade.registerUser(firstName, lastName, username, email);
    }
}
