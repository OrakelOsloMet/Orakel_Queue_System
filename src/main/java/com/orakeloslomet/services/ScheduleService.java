package com.orakeloslomet.services;

import com.orakeloslomet.services.queue.QueueEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author Fredrik Pedersen
 * @since 01/02/2022 at 14:31
 */

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final QueueEntityService queueEntityService;

    @Scheduled(cron = "0 0 0 ? * * *")
    private void moveQueueEntities() {
        queueEntityService.moveAllToStatisticsTable();
    }

}
