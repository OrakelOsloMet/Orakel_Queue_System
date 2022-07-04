package com.orakeloslomet.services.queue;

import com.orakeloslomet.dtos.QueueEntityDTO;
import com.orakeloslomet.persistance.models.queue.QueueEntity;
import com.orakeloslomet.persistance.models.statistics.StatisticsEntity;
import com.orakeloslomet.persistance.repositories.QueueEntityRepository;
import com.orakeloslomet.persistance.repositories.StatisticsRepository;
import com.orakeloslomet.utilities.mappers.QueueEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * @author Fredrik Pedersen
 * @version 1.2
 * @since 01/02/2022 at 15:30
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class QueueEntityServiceImpl implements QueueEntityService {

    private final QueueEntityMapper entityMapper;
    private final QueueEntityRepository repository;
    private final StatisticsRepository statisticsRepository;

    @Override
    public List<QueueEntityDTO> findAll() {
        return mapToDtos(repository.findAll());
    }

    public QueueEntityDTO findById(final Long id) {
        return repository.findById(id)
                .map(entityMapper::toDto)
                .orElseThrow(); //TODO Improve error handling
    }

    @Override
    public QueueEntityDTO save(final QueueEntityDTO queueEntityDTO) {
        return saveAndReturnDto(entityMapper.toEntity(queueEntityDTO));
    }

    @Override
    public QueueEntityDTO update(final QueueEntityDTO queueEntityDTO, final Long id) {
        return repository.findById(id)
                .map(queueEntity -> {
                    final QueueEntity mappedEntity = entityMapper.toEntity(queueEntityDTO);
                    queueEntity.setName(mappedEntity.getName());
                    queueEntity.setSubject(mappedEntity.getSubject());
                    queueEntity.setPlacement(mappedEntity.getPlacement());
                    queueEntity.setComment(mappedEntity.getComment());
                    queueEntity.setStudyYear(mappedEntity.getStudyYear());
                    return saveAndReturnDto(queueEntity);
                }).orElseThrow(() -> new NoSuchElementException(String.format("QueueEntity with ID %s not found", id)));
    }

    //TODO Add exception handling for not-found IDs
    public void deleteById(final Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public Boolean confirmDone(final Long id) {
        final QueueEntity doneEntity = repository.findById(id).orElseThrow();
        final StatisticsEntity statistics = entityMapper.toStatistics(doneEntity);

        statisticsRepository.save(statistics);
        repository.delete(doneEntity);

        return true;
    }

    private QueueEntityDTO saveAndReturnDto(final QueueEntity queueEntity) {
        final QueueEntity savedEntity = repository.save(queueEntity);
        return entityMapper.toDto(savedEntity);
    }

    private List<QueueEntityDTO> mapToDtos(final List<QueueEntity> queueEntities) {
        return queueEntities.stream().map(entityMapper::toDto).collect(Collectors.toList());
    }
}
