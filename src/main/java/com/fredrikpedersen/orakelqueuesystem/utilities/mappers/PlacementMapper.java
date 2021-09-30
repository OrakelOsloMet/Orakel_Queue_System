package com.fredrikpedersen.orakelqueuesystem.utilities.mappers;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.Placement;
import com.fredrikpedersen.orakelqueuesystem.dtos.PlacementDTO;
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
