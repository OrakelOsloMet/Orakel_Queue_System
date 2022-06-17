package com.orakeloslomet.services.queue;

import com.orakeloslomet.dtos.PlacementDTO;
import com.orakeloslomet.persistance.models.queue.Placement;
import com.orakeloslomet.services.CrudService;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 30/09/2021 at 15:39
 */

public interface PlacementService extends CrudService<PlacementDTO, Long> {
}
