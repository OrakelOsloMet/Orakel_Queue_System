package com.fredrikpedersen.orakelqueuesystem.serviceLayer.queue;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.QueueEntity;
import com.fredrikpedersen.orakelqueuesystem.dto.QueueEntityDTO;
import com.fredrikpedersen.orakelqueuesystem.serviceLayer.CrudService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 20/09/2020 at 21:59
 */
public interface QueueEntityService extends CrudService<QueueEntityDTO, QueueEntity, Long> {
    List<QueueEntityDTO> findALlNotDone();
    void confirmDone(Long id);
}
