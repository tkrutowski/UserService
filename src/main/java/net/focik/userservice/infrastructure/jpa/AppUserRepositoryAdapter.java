package net.focik.userservice.infrastructure.jpa;

import lombok.RequiredArgsConstructor;
import net.focik.userservice.domain.AppUser;
import net.focik.userservice.domain.port.secondary.IAppUserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AppUserRepositoryAdapter implements IAppUserRepository {

    private final IUserDtoRepository userDtoRepository;

    @Override
    public AppUser addUser(AppUser user) {
        return null;
    }

    @Override
    public AppUser findUserByUsername(String username) {
        Optional<AppUser> byUserName = userDtoRepository.findByUsername(username);

        if (byUserName.isEmpty())
            return null;

        return byUserName.get();
    }

    @Override
    public AppUser save(AppUser user) {
        return userDtoRepository.save(user);
    }

    @Override
    public List<AppUser> findAll() {
        return userDtoRepository.findAll();
    }

    @Override
    public AppUser findUserByEmail(String email) {
        Optional<AppUser> byEmail = userDtoRepository.findByEmail(email);

        if (byEmail.isEmpty())
            return null;

        return byEmail.get();
    }

    @Override
    public AppUser findUserById(Long id) {
        int i=0;
        Optional<AppUser> byId = userDtoRepository.findById(id);
        if (byId.isEmpty())
            return null;

        return byId.get();
    }
}
