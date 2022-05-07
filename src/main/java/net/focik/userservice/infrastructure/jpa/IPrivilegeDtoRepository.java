package net.focik.userservice.infrastructure.jpa;

import net.focik.userservice.domain.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface IPrivilegeDtoRepository extends JpaRepository<Privilege, Long> {
//    Optional<Privilege> findByName(String name);
}
