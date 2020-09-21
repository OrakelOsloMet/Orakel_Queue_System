package com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.repositories;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
