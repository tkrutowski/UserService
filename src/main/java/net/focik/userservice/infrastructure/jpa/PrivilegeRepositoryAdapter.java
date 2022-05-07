package net.focik.userservice.infrastructure.jpa;

import lombok.RequiredArgsConstructor;
import net.focik.userservice.domain.Privilege;
import net.focik.userservice.domain.port.secondary.IPrivilegeRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PrivilegeRepositoryAdapter implements IPrivilegeRepository {
    private final IPrivilegeDtoRepository privilegeDtoRepository;


    @Override
    public Privilege getPrivilegeById(Long id) {
        Optional<Privilege> byId = privilegeDtoRepository.findById(id);

        if(byId.isEmpty())
            return null;

        return byId.get();
    }

    @Override
    public Privilege save(Privilege privilege) {
        return privilegeDtoRepository.save(privilege);
    }
}
