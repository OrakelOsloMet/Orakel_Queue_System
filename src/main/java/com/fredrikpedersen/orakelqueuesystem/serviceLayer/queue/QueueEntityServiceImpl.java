package com.fredrikpedersen.orakelqueuesystem.serviceLayer.queue;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.QueueEntity;
import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.repositories.QueueEntityRepository;
import com.fredrikpedersen.orakelqueuesystem.dtos.QueueEntityDTO;
import com.fredrikpedersen.orakelqueuesystem.utilities.mappers.QueueEntityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Fredrik Pedersen
 * @since 20/09/2020 at 21:41
 */

@Slf4j
@Service
public class QueueEntityServiceImpl implements QueueEntityService {

    private final QueueEntityMapper queueEntityMapper;
    private final QueueEntityRepository queueEntityRepository;

    public QueueEntityServiceImpl(final QueueEntityMapper queueEntityMapper, final QueueEntityRepository queueEntityRepository) {
        this.queueEntityMapper = queueEntityMapper;
        this.queueEntityRepository = queueEntityRepository;
    }

    @Override
    public List<QueueEntityDTO> findALlNotDone() {
        return this.findAll().stream()
                .filter(queueEntity -> !queueEntity.isConfirmedDone())
                .collect(Collectors.toList());
    }

    @Override
    public List<QueueEntityDTO> findAllDone() {
        return this.findAll().stream()
                .filter(QueueEntityDTO::isConfirmedDone)
                .collect(Collectors.toList());
    }

    @Override
    public List<QueueEntityDTO> findAll() {
        return queueEntityRepository.findAll()
                .stream()
                .map(queueEntityMapper::toDto)
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
        QueueEntity savedEntity = queueEntityRepository.save(queueEntity);
        return queueEntityMapper.toDto(savedEntity);
    }
}
