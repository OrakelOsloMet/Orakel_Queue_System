package com.orakeloslomet.persistance.repositories.authentication;

import com.orakeloslomet.persistance.models.authentication.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Fredrik Pedersen
 * @since 20/09/2020 at 21:45
 */

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
