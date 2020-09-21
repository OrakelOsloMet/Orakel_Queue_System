package com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.repositories;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(final String username);
}
