package com.fredrikpedersen.orakelqueuesystem.serviceLayer.queue;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.Placement;
import com.fredrikpedersen.orakelqueuesystem.dtos.PlacementDTO;
import com.fredrikpedersen.orakelqueuesystem.serviceLayer.CrudService;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 30/09/2021 at 15:39
 */

public interface PlacementService extends CrudService<PlacementDTO, Placement, Long> {
}
