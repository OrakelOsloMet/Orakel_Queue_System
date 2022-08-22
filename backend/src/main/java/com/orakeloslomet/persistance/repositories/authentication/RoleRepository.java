package com.orakeloslomet.persistance.repositories.authentication;

import com.orakeloslomet.persistance.models.authentication.ERole;
import com.orakeloslomet.persistance.models.authentication.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Fredrik Pedersen
 * @since 20/09/2020 at 21:45
 */

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole role);
}
