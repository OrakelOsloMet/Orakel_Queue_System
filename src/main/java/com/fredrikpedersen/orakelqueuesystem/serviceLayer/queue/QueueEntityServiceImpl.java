package com.fredrikpedersen.orakelqueuesystem.serviceLayer.queue;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.QueueEntity;
import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.repositories.QueueEntityRepository;
import com.fredrikpedersen.orakelqueuesystem.dto.QueueEntityDTO;
import com.fredrikpedersen.orakelqueuesystem.utilities.mappers.QueueEntityMapper;
import com.fredrikpedersen.orakelqueuesystem.webLayer.controllers.queue.QueueEntityController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 20/09/2020 at 21:58
 */

@Slf4j
@Service
public class QueueEntityServiceImpl implements QueueEntityService {

    private final String URL = QueueEntityController.BASE_URL;
    private final QueueEntityMapper queueEntityMapper;
    private final QueueEntityRepository queueEntityRepository;

    public QueueEntityServiceImpl(final QueueEntityMapper queueEntityMapper, final QueueEntityRepository queueEntityRepository) {
        this.queueEntityMapper = queueEntityMapper;
        this.queueEntityRepository = queueEntityRepository;
    }

    @Override
    public List<QueueEntityDTO> findAll() {
        return queueEntityRepository.findAll()
                .stream()
                .map(queueEntity -> {
                    QueueEntityDTO queueEntityDTO = queueEntityMapper.queueEntityToQueueEntityDTO(queueEntity);
                    queueEntityDTO.setUrl(URL + queueEntity.getId());
                    return queueEntityDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public QueueEntityDTO createNew(final QueueEntityDTO queueEntityDTO) {
        return saveAndReturnDTO(queueEntityMapper.queueEntityDTOToQueueEntity(queueEntityDTO));
    }

    @Override
    public void delete(final QueueEntityDTO queueEntityDTO) {
        queueEntityRepository.delete(queueEntityMapper.queueEntityDTOToQueueEntity(queueEntityDTO));
    }

    public void deleteById(final Long id) {
        queueEntityRepository.deleteById(id);
    }

    @Override
    public QueueEntityDTO saveAndReturnDTO(final QueueEntity queueEntity) {
        QueueEntity savedEntity = queueEntityRepository.save(queueEntity);

        QueueEntityDTO queueEntityDTO = queueEntityMapper.queueEntityToQueueEntityDTO(savedEntity);
        queueEntityDTO.setUrl(URL + savedEntity.getId());

        return queueEntityDTO;
    }
}
