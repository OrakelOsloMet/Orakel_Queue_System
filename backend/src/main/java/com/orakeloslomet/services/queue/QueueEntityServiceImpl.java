package com.orakeloslomet.services.queue;

import com.orakeloslomet.dtos.QueueEntityDTO;
import com.orakeloslomet.mappers.QueueEntityMapper;
import com.orakeloslomet.persistance.models.queue.QueueEntity;
import com.orakeloslomet.persistance.models.statistics.StatisticsEntity;
import com.orakeloslomet.persistance.repositories.queue.QueueEntityRepository;
import com.orakeloslomet.persistance.repositories.statistics.StatisticsRepository;
import com.orakeloslomet.services.CrudServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.requireNonNull;

/**
 * @author Fredrik Pedersen
 * @version 1.3
 * @since 08/07/2022 at 19:38
 */

@Service
public class QueueEntityServiceImpl extends CrudServiceImpl<QueueEntityDTO, QueueEntity> implements QueueEntityService {
    private final StatisticsRepository statisticsRepository;
    private final QueueEntityMapper queueEntityMapper;

    public QueueEntityServiceImpl(final QueueEntityMapper dtoMapper,
                                  final QueueEntityRepository repository,
                                  final StatisticsRepository statisticsRepository) {
        super(dtoMapper, repository);
        this.statisticsRepository = requireNonNull(statisticsRepository);
        this.queueEntityMapper = requireNonNull(dtoMapper);
    }

    @Override
    @Transactional
    public Boolean confirmDone(final Long id) {
        final QueueEntity doneEntity = repository.findById(id).orElseThrow();
        final StatisticsEntity statistics = queueEntityMapper.toStatistics(doneEntity);

        statisticsRepository.save(statistics);
        repository.delete(doneEntity);

        return true;
    }


}
