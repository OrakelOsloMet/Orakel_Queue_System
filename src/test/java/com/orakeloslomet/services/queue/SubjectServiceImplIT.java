package com.orakeloslomet.services.queue;

import com.orakeloslomet.dtos.SubjectDTO;
import com.orakeloslomet.persistance.models.queue.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class SubjectServiceImplIT {

    @Autowired
    private SubjectServiceImpl subjectService;

    @Test
    void test() {
        final List<SubjectDTO> subjects = subjectService.findSubjectsCurrentSemester();

        subjects.forEach(System.out::println);
    }

}