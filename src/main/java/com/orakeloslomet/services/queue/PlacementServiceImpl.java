package com.orakeloslomet.services.queue;

import com.orakeloslomet.dtos.PlacementDTO;
import com.orakeloslomet.persistance.models.queue.Placement;
import com.orakeloslomet.persistance.repositories.PlacementRepository;
import com.orakeloslomet.utilities.mappers.PlacementMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 30/09/2021 at 15:41
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class PlacementServiceImpl implements PlacementService {

    private final PlacementMapper entityMapper;
    private final PlacementRepository repository;

    @Override
    public List<PlacementDTO> findAll() {
        return repository.findAll().stream()
                .map(entityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PlacementDTO findById(final Long id) {
        return repository.findById(id)
                .map(entityMapper::toDto)
                .orElseThrow(); //TODO Better error handling
    }

    @Override
    public PlacementDTO save(final PlacementDTO placementDTO) {
        return saveAndReturnDto(entityMapper.toEntity(placementDTO));
    }

    @Override
    @Transactional
    public PlacementDTO update(final PlacementDTO placementDTO, final Long id) {
        return repository.findById(id)
                .map(placement -> {
                    placement.setPrefix(placementDTO.getPrefix());
                    placement.setPlacements(placementDTO.getNumber());
                    return saveAndReturnDto(placement);
                }).orElseThrow(() -> new EntityNotFoundException(String.format("Subject with ID %s not found!", id)));
    }

    @Override
    public void deleteById(final Long id) {
        repository.deleteById(id);
    }

    private PlacementDTO saveAndReturnDto(final Placement placement) {
        final Placement savedEntity = repository.save(placement);
        return entityMapper.toDto(savedEntity);
    }
}
