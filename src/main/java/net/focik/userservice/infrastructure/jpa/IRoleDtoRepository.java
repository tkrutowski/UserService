package net.focik.userservice.infrastructure.jpa;

import net.focik.userservice.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleDtoRepository extends JpaRepository<Role, Long> {
}
