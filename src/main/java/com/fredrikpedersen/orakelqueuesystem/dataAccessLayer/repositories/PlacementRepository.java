package com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.repositories;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.Placement;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 30/09/2021 at 14:21
 */

public interface PlacementRepository extends JpaRepository<Placement, Long> {
}
