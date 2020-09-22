package com.fredrikpedersen.orakelqueuesystem.utilities.mappers;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.QueueEntity;
import com.fredrikpedersen.orakelqueuesystem.dto.QueueEntityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 20/09/2020 at 22:13
 */

@Mapper(componentModel = "spring")
public interface QueueEntityMapper {

    QueueEntity queueEntityDTOToQueueEntity(final QueueEntityDTO queueEntityDTO);

    @Mapping(target = "url", source = "")
    QueueEntityDTO queueEntityToQueueEntityDTO(final QueueEntity queueEntity);
}
