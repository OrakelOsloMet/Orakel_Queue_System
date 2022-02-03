package com.orakeloslomet.services.queue;

import com.orakeloslomet.dtos.QueueEntityDTO;
import com.orakeloslomet.persistance.models.queue.QueueEntity;
import com.orakeloslomet.services.CrudService;

import java.util.List;

/**
 * @author Fredrik Pedersen
 * @since 20/09/2020 at 21:41
 */

public interface QueueEntityService extends CrudService<QueueEntityDTO, QueueEntity, Long> {

    void confirmDone(Long id);
}
