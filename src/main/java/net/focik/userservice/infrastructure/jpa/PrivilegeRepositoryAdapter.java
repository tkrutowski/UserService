package net.focik.userservice.infrastructure.jpa;

import lombok.RequiredArgsConstructor;
import net.focik.userservice.domain.Privilege;
import net.focik.userservice.domain.Role;
import net.focik.userservice.domain.port.secondary.IPrivilegeRepository;
import net.focik.userservice.domain.port.secondary.IRoleRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PrivilegeRepositoryAdapter implements IPrivilegeRepository {
    private final IPrivilegeDtoRepository privilegeDtoRepository;


    @Override
    public Privilege getPrivilegeByName(String name) {
        Optional<Privilege> byId = privilegeDtoRepository.findByName(name);

        if(byId.isEmpty())
            return null;

        return byId.get();
    }
}
