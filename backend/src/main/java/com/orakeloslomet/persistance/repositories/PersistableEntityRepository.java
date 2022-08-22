package com.orakeloslomet.persistance.repositories;

import com.orakeloslomet.persistance.models.PersistableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersistableEntityRepository<T extends PersistableEntity> extends JpaRepository<T, Long> {
}
