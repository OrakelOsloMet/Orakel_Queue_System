package com.fredrikpedersen.orakelqueuesystem.serviceLayer.queue;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.QueueEntity;
import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.repositories.QueueEntityRepository;
import com.fredrikpedersen.orakelqueuesystem.dtos.QueueEntityDTO;
import com.fredrikpedersen.orakelqueuesystem.utilities.mappers.QueueEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    private final QueueEntityMapper queueEntityMapper;
    private final QueueEntityRepository queueEntityRepository;

    @Override
    public List<QueueEntityDTO> findAll() {
        return queueEntityRepository.findAll()
                .stream()
                .map(queueEntityMapper::toDto)
                .collect(Collectors.toList());
    }

    public QueueEntityDTO findById(final Long id) {
        return queueEntityRepository.findById(id)
                .map(queueEntityMapper::toDto)
                .orElseThrow(RuntimeException::new); //TODO Improve error handling
    }

    @Override
    public List<QueueEntityDTO> findALlNotDone() {
        return this.findAll().stream()
                .filter(entityDTO -> entityDTO.getTimeConfirmedDone() == null)
                .collect(Collectors.toList());
    }

    @Override
    public List<QueueEntityDTO> findAllDone() {
        return this.findAll().stream()
                .filter(entityDTO -> entityDTO.getTimeConfirmedDone() != null)
                .collect(Collectors.toList());
    }

    @Override
    public QueueEntityDTO createNew(final QueueEntityDTO queueEntityDTO) {
        return saveAndReturnDTO(queueEntityMapper.toEntity(queueEntityDTO));
    }

    @Override
    public QueueEntityDTO edit(final QueueEntityDTO queueEntityDTO, final Long id) {
        throw new UnsupportedOperationException("NOT IMPLEMENTED... YET!");
    }

    //TODO Add exception handling for not-found IDs

    public void deleteById(final Long id) {
        queueEntityRepository.deleteById(id);
    }

    @Override
    public void confirmDone(final Long id) {

        if (queueEntityRepository.findById(id).isPresent()) {
            QueueEntity doneEntity = queueEntityRepository.findById(id).get();
            doneEntity.markAsDone();
            queueEntityRepository.save(doneEntity);
        }
    }

    @Override
    public QueueEntityDTO saveAndReturnDTO(final QueueEntity queueEntity) {
        final QueueEntity savedEntity = queueEntityRepository.save(queueEntity);
        return queueEntityMapper.toDto(savedEntity);
    }
}
