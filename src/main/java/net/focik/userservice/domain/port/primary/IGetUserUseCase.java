package net.focik.userservice.domain.port.primary;

import net.focik.userservice.domain.AppUser;

import java.util.List;

public interface IGetUserUseCase {
    AppUser findUserByUsername(String username);
    AppUser findUserById(Long id);
    List<AppUser> getAllUsers();

}
