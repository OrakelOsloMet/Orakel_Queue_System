package com.fredrikpedersen.orakelqueuesystem.utilities.mappers;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.QueueEntity;
import com.fredrikpedersen.orakelqueuesystem.dtos.QueueEntityDTO;
import org.mapstruct.Mapper;

/**
 * @author Fredrik Pedersen
 * @since 19/01/2021 at 14:48
 */

@Mapper(componentModel = "spring")
public interface QueueEntityMapper extends IEntityMapper<QueueEntityDTO, QueueEntity> {

    QueueEntityDTO toDto(final QueueEntity queueEntity);
    QueueEntity toEntity(final QueueEntityDTO queueEntityDTO);
}
