package net.focik.userservice.infrastructure.dto;

import net.focik.userservice.domain.Role;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "roles")
public class RoleDbDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
