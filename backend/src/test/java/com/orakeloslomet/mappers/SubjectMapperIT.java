package com.orakeloslomet.mappers;

import com.orakeloslomet.dtos.SubjectDTO;
import com.orakeloslomet.persistance.models.queue.ESemester;
import com.orakeloslomet.persistance.models.queue.Subject;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {SubjectMapperImpl.class})
class SubjectMapperIT {

    @Autowired
    private SubjectMapperImpl classUnderTest;

    @Nested
    class toDto {

        @Test
        void mapsCorrectly() {
            final Subject domainObject = Subject.builder()
                    .id(1L)
                    .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                    .semester(ESemester.AUTUMN)
                    .name("SUBJECT")
                    .build();

            final SubjectDTO actualResult = classUnderTest.toDto(domainObject);

            assertEquals(domainObject.getId(), actualResult.getId());
            assertEquals(domainObject.getCreatedDate(), actualResult.getCreatedDate());
            assertEquals(domainObject.getName(), actualResult.getName());
            assertEquals(domainObject.getSemester(), ESemester.fromString(actualResult.getSemester()));
        }
    }

    @Nested
    class toEntity {

        @Test
        void mapsCorrectly() {
            final SubjectDTO dtoObject = SubjectDTO.builder()
                    .id(1L)
                    .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                    .semester(ESemester.AUTUMN.name())
                    .name("SUBJECT")
                    .build();


            final Subject actualResult = classUnderTest.toEntity(dtoObject);

            assertEquals(dtoObject.getId(), actualResult.getId());
            assertEquals(dtoObject.getCreatedDate(), actualResult.getCreatedDate());
            assertEquals(dtoObject.getName(), actualResult.getName());
            assertEquals(ESemester.fromString(dtoObject.getSemester()), actualResult.getSemester());
        }
    }

}