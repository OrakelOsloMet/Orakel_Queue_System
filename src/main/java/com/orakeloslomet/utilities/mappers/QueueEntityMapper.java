package com.orakeloslomet.utilities.mappers;

import com.orakeloslomet.dtos.QueueEntityDTO;
import com.orakeloslomet.persistance.models.queue.QueueEntity;
import com.orakeloslomet.persistance.models.statistics.StatisticsEntity;
import org.mapstruct.Mapper;

/**
 * @author Fredrik Pedersen
 * @since 19/01/2021 at 14:48
 */

@Mapper(componentModel = "spring", uses = {PlacementMapper.class})
public interface QueueEntityMapper extends DtoMapper<QueueEntityDTO, QueueEntity> {

    StatisticsEntity toStatistics(QueueEntity entity);
}
