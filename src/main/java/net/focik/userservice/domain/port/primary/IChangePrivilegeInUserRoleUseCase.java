package net.focik.userservice.domain.port.primary;

import java.util.List;

public interface IChangePrivilegeInUserRoleUseCase {
    void changePrivilegesInUserRole(Long idUser, Long idRole, List<String> privilegesToAdd);
}
