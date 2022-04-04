package net.focik.userservice.domain.port.primary;

import net.focik.userservice.domain.Role;

public interface IAddRoleToUserUseCase {
    void addRoleToUser(Long idUser, Long idRole);
}
