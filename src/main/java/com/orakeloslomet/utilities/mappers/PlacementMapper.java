package com.orakeloslomet.utilities.mappers;

import com.orakeloslomet.dtos.PlacementDTO;
import com.orakeloslomet.persistance.models.queue.Placement;
import org.mapstruct.Mapper;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 30/09/2021 at 15:42
 */

@Mapper(componentModel = "spring")
public interface PlacementMapper extends DtoMapper<PlacementDTO, Placement> {
}
