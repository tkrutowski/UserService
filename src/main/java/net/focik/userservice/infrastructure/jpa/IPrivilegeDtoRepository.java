package net.focik.userservice.infrastructure.jpa;

import net.focik.userservice.domain.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

interface IPrivilegeDtoRepository extends JpaRepository<Privilege, Long> {
}
