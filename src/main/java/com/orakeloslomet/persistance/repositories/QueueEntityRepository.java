package com.orakeloslomet.persistance.repositories;

import com.orakeloslomet.persistance.models.queue.QueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Fredrik Pedersen
 * @since 20/09/2020 at 21:40
 */

public interface QueueEntityRepository extends JpaRepository<QueueEntity, Long> {

    @Query("SELECT q from QueueEntity q WHERE q.timeConfirmedDone IS NULL")
    List<QueueEntity> findAllWhereTimeConfirmedDoneNull();
}
