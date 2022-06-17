package com.orakeloslomet.persistance.repositories;

import com.orakeloslomet.persistance.models.queue.ESemester;
import com.orakeloslomet.persistance.models.queue.Placement;
import com.orakeloslomet.persistance.models.queue.Subject;
import com.orakeloslomet.utilities.constants.Profiles;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests on data defined in V2__init_values.sql for H2 database.
 */

@SpringBootTest
@ActiveProfiles(Profiles.TEST)
class SubjectRepositoryIT {

    @Autowired
    private SubjectRepository subjectRepository;


    @Nested
    class findAllBySemester {

        @Test
        void returnsSubjectsWithCorrectSemester() {

            //given
            final List<ESemester> semesters = Arrays.asList(ESemester.values());

            semesters.forEach(semester -> {
                //when
                final List<Subject> subjects = subjectRepository.findAllBySemester(semester);

                //then
                subjects.forEach(subject -> assertEquals(semester, subject.getSemester()));
            });
        }
    }
}