package com.orakeloslomet.utilities.mappers;

import com.orakeloslomet.dtos.QueueEntityDTO;
import com.orakeloslomet.persistance.models.queue.QueueEntity;
import org.mapstruct.Mapper;

/**
 * @author Fredrik Pedersen
 * @since 19/01/2021 at 14:48
 */

@Mapper(componentModel = "spring", uses = {PlacementMapper.class})
public interface QueueEntityMapper extends IEntityMapper<QueueEntityDTO, QueueEntity> {

    QueueEntityDTO toDto(final QueueEntity queueEntity);
    QueueEntity toEntity(final QueueEntityDTO queueEntityDTO);
}
