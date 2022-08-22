package com.orakeloslomet.mappers;

import com.orakeloslomet.dtos.PlacementDTO;
import com.orakeloslomet.dtos.QueueEntityDTO;
import com.orakeloslomet.dtos.SubjectDTO;
import com.orakeloslomet.persistance.models.queue.ESemester;
import com.orakeloslomet.persistance.models.queue.Placement;
import com.orakeloslomet.persistance.models.queue.QueueEntity;
import com.orakeloslomet.persistance.models.queue.Subject;
import com.orakeloslomet.persistance.models.statistics.StatisticsEntity;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {QueueEntityMapperImpl.class, SubjectMapperImpl.class, PlacementMapperImpl.class})
class QueueEntityMapperIT {

    @Autowired
    private QueueEntityMapper classUnderTest;

    @Nested
    class toDto {

        @Test
        void mapsCorrectly() {
            final Timestamp createdDate = Timestamp.valueOf(LocalDateTime.now());
            final Subject domainSubject = Subject.builder()
                    .id(1L)
                    .createdDate(createdDate)
                    .semester(ESemester.AUTUMN)
                    .name("SUBJECT")
                    .build();
            final Placement domainPlacement = Placement.builder()
                    .id(1L)
                    .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                    .name("PLACEMENT")
                    .number(1)
                    .build();
            final QueueEntity domainObject = QueueEntity.builder()
                    .id(1L)
                    .createdDate(createdDate)
                    .name("QUEUEENTITY")
                    .subject(domainSubject)
                    .placement(domainPlacement)
                    .comment("MOCK")
                    .studyYear(1)
                    .build();

            final QueueEntityDTO actualResult = classUnderTest.toDto(domainObject);

            assertEquals(domainObject.getId(), actualResult.getId());
            assertEquals(domainObject.getCreatedDate(), actualResult.getCreatedDate());
            assertEquals(domainObject.getName(), actualResult.getName());
            assertEquals(domainObject.getComment(), actualResult.getComment());
            assertEquals(domainObject.getStudyYear(), actualResult.getStudyYear());

            final SubjectDTO mappedSubject = actualResult.getSubject();
            assertEquals(domainSubject.getId(), mappedSubject.getId());
            assertEquals(domainSubject.getCreatedDate(), mappedSubject.getCreatedDate());
            assertEquals(domainSubject.getName(), mappedSubject.getName());
            assertEquals(domainSubject.getSemester(), ESemester.fromString(mappedSubject.getSemester()));

            final PlacementDTO mappedPlacement = actualResult.getPlacement();
            assertEquals(domainPlacement.getId(), mappedPlacement.getId());
            assertEquals(domainPlacement.getNumber(), mappedPlacement.getNumber());
            assertEquals(domainPlacement.getCreatedDate(), mappedPlacement.getCreatedDate());
            assertEquals(domainPlacement.getName(), mappedPlacement.getName());
        }
    }

    @Nested
    class toEntity {

        @Test
        void mapsCorrectly() {
            final Timestamp createdDate = Timestamp.valueOf(LocalDateTime.now());
            final SubjectDTO dtoSubject = SubjectDTO.builder()
                    .id(1L)
                    .createdDate(createdDate)
                    .semester(ESemester.AUTUMN.name())
                    .name("SUBJECT")
                    .build();
            final PlacementDTO dtoPlacement = PlacementDTO.builder()
                    .id(1L)
                    .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                    .name("PLACEMENT")
                    .number(1)
                    .build();
            final QueueEntityDTO dtoObject = QueueEntityDTO.builder()
                    .id(1L)
                    .createdDate(createdDate)
                    .name("QUEUEENTITY")
                    .subject(dtoSubject)
                    .placement(dtoPlacement)
                    .comment("MOCK")
                    .studyYear(1)
                    .build();

            final QueueEntity actualResult = classUnderTest.toEntity(dtoObject);

            assertEquals(dtoObject.getId(), actualResult.getId());
            assertEquals(dtoObject.getCreatedDate(), actualResult.getCreatedDate());
            assertEquals(dtoObject.getName(), actualResult.getName());
            assertEquals(dtoObject.getComment(), dtoObject.getComment());
            assertEquals(dtoObject.getStudyYear(), dtoObject.getStudyYear());

            final Subject mappedSubject = actualResult.getSubject();
            assertEquals(dtoSubject.getId(), mappedSubject.getId());
            assertEquals(dtoSubject.getCreatedDate(), mappedSubject.getCreatedDate());
            assertEquals(dtoSubject.getName(), mappedSubject.getName());
            assertEquals(ESemester.fromString(dtoSubject.getSemester()), mappedSubject.getSemester());

            final Placement mappedPlacement = actualResult.getPlacement();
            assertEquals(dtoPlacement.getId(), mappedPlacement.getId());
            assertEquals(dtoPlacement.getNumber(), mappedPlacement.getNumber());
            assertEquals(dtoPlacement.getCreatedDate(), mappedPlacement.getCreatedDate());
            assertEquals(dtoPlacement.getName(), mappedPlacement.getName());
        }
    }

    @Nested
    class toStatistics {

        @Test
        void mapsCorrectly() {

            final Timestamp createdDate = Timestamp.valueOf(LocalDateTime.now());
            final Subject domainSubject = Subject.builder()
                    .id(1L)
                    .createdDate(createdDate)
                    .semester(ESemester.AUTUMN)
                    .name("SUBJECT")
                    .build();
            final Placement domainPlacement = Placement.builder()
                    .id(1L)
                    .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                    .name("PLACEMENT")
                    .number(1)
                    .build();
            final QueueEntity domainObject = QueueEntity.builder()
                    .id(1L)
                    .createdDate(createdDate)
                    .name("QUEUEENTITY")
                    .subject(domainSubject)
                    .placement(domainPlacement)
                    .comment("MOCK")
                    .studyYear(1)
                    .build();

            final StatisticsEntity actualResult = classUnderTest.toStatistics(domainObject);

            assertEquals(domainObject.getId(), actualResult.getId());
            assertEquals(domainObject.getCreatedDate(), actualResult.getCreatedDate());
            assertEquals(domainObject.getSubject(), actualResult.getSubject());
            assertEquals(domainObject.getPlacement(), actualResult.getPlacement());
            assertEquals(domainObject.getStudyYear(), actualResult.getStudyYear());

        }
    }

}