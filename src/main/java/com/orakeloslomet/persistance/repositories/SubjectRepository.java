package com.orakeloslomet.persistance.repositories;

import com.orakeloslomet.persistance.models.queue.ESemester;
import com.orakeloslomet.persistance.models.queue.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Fredrik Pedersen
 * @since 19/01/2021 at 12:22
 */

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query("SELECT s FROM Subject s WHERE s.semester = :semester")
    List<Subject> findAllBySemester(@Param("semester") ESemester semester);
}
