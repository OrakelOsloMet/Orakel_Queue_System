package com.orakeloslomet.services.queue;

import com.orakeloslomet.persistance.models.queue.Placement;
import com.orakeloslomet.persistance.repositories.PlacementRepository;
import com.orakeloslomet.dtos.PlacementDTO;
import com.orakeloslomet.utilities.mappers.PlacementMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    private final PlacementRepository placementRepository;
    private final PlacementMapper placementMapper;

    @Override
    public List<PlacementDTO> findAll() {
        return placementRepository.findAll().stream()
                .map(placementMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PlacementDTO findById(final Long id) {
        return placementRepository.findById(id)
                .map(placementMapper::toDto)
                .orElseThrow(RuntimeException::new); //TODO Better error handling
    }

    @Override
    public PlacementDTO createNew(final PlacementDTO placementDTO) {
        return saveAndReturnDTO(placementMapper.toEntity(placementDTO));
    }

    @Override
    public PlacementDTO edit(final PlacementDTO placementDTO, final Long id) {
        return placementRepository.findById(id)
                .map(placement -> {
                    placement.setPrefix(placementDTO.getPrefix());
                    placement.setNumber(placementDTO.getNumber());
                    return saveAndReturnDTO(placement);
                }).orElseThrow(() -> new EntityNotFoundException(String.format("Subject with ID %s not found!", id)));
    }

    @Override
    public void deleteById(final Long id) {
        placementRepository.deleteById(id);
    }

    @Override
    public PlacementDTO saveAndReturnDTO(final Placement placement) {
        final Placement savedEntity = placementRepository.save(placement);
        return placementMapper.toDto(savedEntity);
    }
}
