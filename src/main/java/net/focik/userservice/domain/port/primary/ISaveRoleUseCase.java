package net.focik.userservice.domain.port.primary;

import net.focik.userservice.domain.Role;

public interface ISaveRoleUseCase {
    Role saveRole(Role role);
}
