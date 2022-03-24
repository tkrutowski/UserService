package net.focik.userservice.infrastructure.jpa;

import net.focik.userservice.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface IUserDtoRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> getByUsername(String username);
}
