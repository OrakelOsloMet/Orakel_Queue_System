package com.fredrikpedersen.orakelqueuesystem.serviceLayer;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.QueueEntity;
import com.fredrikpedersen.orakelqueuesystem.dto.QueueEntityDTO;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 20/09/2020 at 21:59
 */
public interface QueueEntityService extends CrudService<QueueEntityDTO, QueueEntity> {
}
