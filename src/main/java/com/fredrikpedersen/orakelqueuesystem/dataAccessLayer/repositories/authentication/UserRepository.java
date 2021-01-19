package com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.repositories.authentication;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.authentication.User;
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
