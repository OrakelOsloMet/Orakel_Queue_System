package com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.repositories;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.QueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 20/09/2020 at 21:40
 */
public interface QueueEntityRepository extends JpaRepository<QueueEntity, Long> {
}
