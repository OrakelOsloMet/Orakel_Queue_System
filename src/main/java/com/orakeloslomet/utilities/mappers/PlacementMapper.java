package com.orakeloslomet.utilities.mappers;

import com.orakeloslomet.persistance.models.queue.Placement;
import com.orakeloslomet.dtos.PlacementDTO;
import org.mapstruct.Mapper;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 30/09/2021 at 15:42
 */

@Mapper(componentModel = "spring")
public interface PlacementMapper extends IEntityMapper<PlacementDTO, Placement> {

    PlacementDTO toDto(Placement placement);
    Placement toEntity(PlacementDTO placementDTO);
}
