package com.fredrikpedersen.orakelqueuesystem.serviceLayer.queue;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.QueueEntity;
import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.repositories.QueueEntityRepository;
import com.fredrikpedersen.orakelqueuesystem.dto.QueueEntityDTO;
import com.fredrikpedersen.orakelqueuesystem.utilities.constants.URLs;
import com.fredrikpedersen.orakelqueuesystem.utilities.mappers.QueueEntityMapper;
import com.fredrikpedersen.orakelqueuesystem.webLayer.controllers.queue.QueueEntityController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 20/09/2020 at 21:58
 */

@Slf4j
@Service
public class QueueEntityServiceImpl implements QueueEntityService {

    private final String URL = URLs.QUEUE_BASE_URL;
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
    public List<QueueEntityDTO> findAll() {
        return queueEntityRepository.findAll()
                .stream()
                .map(queueEntity -> {
                    QueueEntityDTO queueEntityDTO = queueEntityMapper.toDto(queueEntity);
                    queueEntityDTO.setUrl(URL + queueEntity.getId());
                    return queueEntityDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public QueueEntityDTO createNew(final QueueEntityDTO queueEntityDTO) {
        return saveAndReturnDTO(queueEntityMapper.toEntity(queueEntityDTO));
    }

    @Override
    public void delete(final QueueEntityDTO queueEntityDTO) {
        queueEntityRepository.delete(queueEntityMapper.toEntity(queueEntityDTO));
    }

    public void deleteById(final Long id) {
        queueEntityRepository.deleteById(id);
    }

    @Override
    public void confirmDone(final Long id) {

        if (queueEntityRepository.findById(id).isPresent()) {
            QueueEntity doneEntity = queueEntityRepository.findById(id).get();
            doneEntity.setConfirmedDone(true);
            doneEntity.setTimeConfirmedDone(new Date());
            queueEntityRepository.save(doneEntity);
        }

        //TODO Throw an exception if an invalid id is passed here
    }

    @Override
    public QueueEntityDTO saveAndReturnDTO(final QueueEntity queueEntity) {
        QueueEntity savedEntity = queueEntityRepository.save(queueEntity);

        QueueEntityDTO queueEntityDTO = queueEntityMapper.toDto(savedEntity);
        queueEntityDTO.setUrl(URL + savedEntity.getId());

        return queueEntityDTO;
    }
}