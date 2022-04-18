package net.focik.userservice.domain.port.primary;

import net.focik.userservice.domain.AppUser;

public interface IUpdateUserUseCase {

    AppUser updateUser(Long id, String firstName, String lastName, String username, String email);

    void updateIsActive(Long id, boolean isActive);

    void updateIsLock(Long id, boolean isLock);
}
