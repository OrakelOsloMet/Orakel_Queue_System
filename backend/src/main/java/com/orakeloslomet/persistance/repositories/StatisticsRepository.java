package com.orakeloslomet.persistance.repositories;

import com.orakeloslomet.persistance.models.statistics.StatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Fredrik Pedersen
 * @since 01/02/2022 at 15:21
 */

public interface StatisticsRepository extends JpaRepository<StatisticsEntity, Long> {
}
