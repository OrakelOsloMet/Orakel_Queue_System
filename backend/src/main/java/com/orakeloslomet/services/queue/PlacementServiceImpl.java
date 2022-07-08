package com.orakeloslomet.services.queue;

import com.orakeloslomet.dtos.PlacementDTO;
import com.orakeloslomet.mappers.PlacementMapper;
import com.orakeloslomet.persistance.models.queue.Placement;
import com.orakeloslomet.persistance.repositories.queue.PlacementRepository;
import com.orakeloslomet.services.CrudServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Fredrik Pedersen
 * @version 1.1
 * @since 08/07/2022 at 19:38
 */

@Slf4j
@Service
public class PlacementServiceImpl extends CrudServiceImpl<PlacementDTO, Placement> implements PlacementService {

    public PlacementServiceImpl(final PlacementMapper dtoMapper, final PlacementRepository repository) {
        super(dtoMapper, repository);
    }
}
