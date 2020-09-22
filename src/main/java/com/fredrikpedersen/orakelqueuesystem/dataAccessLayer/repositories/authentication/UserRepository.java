package com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.repositories.authentication;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.authentication.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(final String username);
    Boolean existsByUsername(final String username);
    Boolean existsByEmail(final String email);
}
