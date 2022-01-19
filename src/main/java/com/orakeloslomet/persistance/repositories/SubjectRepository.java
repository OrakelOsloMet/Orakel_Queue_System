package com.orakeloslomet.persistance.repositories;

import com.orakeloslomet.persistance.models.queue.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Fredrik Pedersen
 * @since 19/01/2021 at 12:22
 */

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
