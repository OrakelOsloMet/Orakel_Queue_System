package com.fredrikpedersen.orakelqueuesystem.serviceLayer.queue;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.QueueEntity;
import com.fredrikpedersen.orakelqueuesystem.dto.QueueEntityDTO;
import com.fredrikpedersen.orakelqueuesystem.serviceLayer.CrudService;

import java.util.List;

public interface QueueEntityService extends CrudService<QueueEntityDTO, QueueEntity, Long> {
    List<QueueEntityDTO> findALlNotDone();
    void confirmDone(Long id);
}
