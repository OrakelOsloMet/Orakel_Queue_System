package com.orakeloslomet.services.queue;

import com.orakeloslomet.dtos.QueueEntityDTO;
import com.orakeloslomet.persistance.models.queue.QueueEntity;
import com.orakeloslomet.persistance.repositories.QueueEntityRepository;
import com.orakeloslomet.utilities.mappers.QueueEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Fredrik Pedersen
 * @version 1.1
 * @since 30/09/2021 at 14:31
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class QueueEntityServiceImpl implements QueueEntityService {

    private final QueueEntityMapper entityMapper;
    private final QueueEntityRepository repository;

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
    public List<QueueEntityDTO> findALlNotDone() {
        return mapToDtos(repository.findAllWhereTimeConfirmedDoneNull());
    }

    @Override
    public List<QueueEntityDTO> findAllDone() {
        return mapToDtos(repository.findAllWhereTimeConfirmedDoneNotNull());
    }

    @Override
    public QueueEntityDTO save(final QueueEntityDTO queueEntityDTO) {
        return saveAndReturnDto(entityMapper.toEntity(queueEntityDTO));
    }

    @Override
    public QueueEntityDTO update(final QueueEntityDTO queueEntityDTO, final Long id) {
        throw new UnsupportedOperationException("NOT IMPLEMENTED... YET!");
    }

    //TODO Add exception handling for not-found IDs
    public void deleteById(final Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void confirmDone(final Long id) {
        if (repository.findById(id).isPresent()) {
            final QueueEntity doneEntity = repository.findById(id).get();
            doneEntity.markAsDone();

            repository.save(doneEntity);
        }
    }

    private QueueEntityDTO saveAndReturnDto(final QueueEntity queueEntity) {
        final QueueEntity savedEntity = repository.save(queueEntity);
        return entityMapper.toDto(savedEntity);
    }

    private List<QueueEntityDTO> mapToDtos(final List<QueueEntity> queueEntities) {
        return queueEntities.stream().map(entityMapper::toDto).collect(Collectors.toList());
    }
}
