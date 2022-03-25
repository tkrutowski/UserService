package net.focik.userservice.domain.port.primary;

import net.focik.userservice.domain.AppUser;

public interface IAddNewUserUseCase {

    AppUser addNewUser(String firstName, String lastName, String username, String password, String email, boolean enabled,
                       boolean isNotLocked);
}
