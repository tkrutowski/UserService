package net.focik.userservice.domain.port.secondary;

import net.focik.userservice.domain.AppUser;

import java.util.List;
import java.util.Optional;

public interface IAppUserRepository {
    AppUser addUser(AppUser user);
    AppUser findUserByUsername(String username);

    AppUser save(AppUser user);

    List<AppUser> findAll();

    AppUser findUserByEmail(String email);

    AppUser findUserById(Long id);

    void deleteUser(Long id);
}
