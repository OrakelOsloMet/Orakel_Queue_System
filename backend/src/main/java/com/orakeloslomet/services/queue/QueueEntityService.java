package com.orakeloslomet.services.queue;

import com.orakeloslomet.dtos.QueueEntityDTO;
import com.orakeloslomet.services.CrudService;

/**
 * @author Fredrik Pedersen
 * @since 20/09/2020 at 21:41
 */

public interface QueueEntityService extends CrudService<QueueEntityDTO> {

    Boolean confirmDone(Long id);
}
