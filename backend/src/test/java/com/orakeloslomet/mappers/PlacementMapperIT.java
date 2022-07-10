package com.orakeloslomet.mappers;

import com.orakeloslomet.dtos.PlacementDTO;
import com.orakeloslomet.persistance.models.queue.Placement;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {PlacementMapperImpl.class})
class PlacementMapperIT {

    @Autowired
    private PlacementMapper classUnderTest;

    @Nested
    class toDto {

        @Test
        void mapsCorrectly() {
            final Placement domainObject = Placement.builder()
                    .id(1L)
                    .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                    .name("MOCK")
                    .number(1)
                    .build();

            final PlacementDTO actualResult = classUnderTest.toDto(domainObject);

            assertEquals(domainObject.getId(), actualResult.getId());
            assertEquals(domainObject.getCreatedDate(), actualResult.getCreatedDate());
            assertEquals(domainObject.getName(), actualResult.getName());
            assertEquals(domainObject.getNumber(), actualResult.getNumber());
        }
    }

    @Nested
    class toEntity {

        @Test
        void mapsCorrectly() {
            final PlacementDTO dtoObject = PlacementDTO.builder()
                    .id(1L)
                    .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                    .name("MOCK")
                    .number(1)
                    .build();

            final Placement actualResult = classUnderTest.toEntity(dtoObject);

            assertEquals(dtoObject.getId(), actualResult.getId());
            assertEquals(dtoObject.getCreatedDate(), actualResult.getCreatedDate());
            assertEquals(dtoObject.getName(), actualResult.getName());
            assertEquals(dtoObject.getNumber(), actualResult.getNumber());
        }
    }

}