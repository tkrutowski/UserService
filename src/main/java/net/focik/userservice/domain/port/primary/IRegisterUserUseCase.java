package net.focik.userservice.domain.port.primary;

import net.focik.userservice.domain.AppUser;

public interface IRegisterUserUseCase {

    AppUser registerUser(String firstName, String lastName, String username, String email);
}
