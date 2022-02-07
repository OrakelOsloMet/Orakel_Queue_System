package com.orakeloslomet.services.resources;

import com.orakeloslomet.persistance.models.queue.ESemester;
import com.orakeloslomet.persistance.models.queue.Placement;
import com.orakeloslomet.persistance.models.queue.Subject;
import com.orakeloslomet.persistance.models.statistics.StatisticsEntity;
import com.orakeloslomet.persistance.repositories.StatisticsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.InputStreamResource;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QueueStatisticsExportServiceTest extends CSVParsingTestBase {

    @Mock
    private StatisticsRepository statisticsRepository;

    private QueueStatisticsExportService queueStatisticsExportService;

    @BeforeEach
    void setup() {
        queueStatisticsExportService = new QueueStatisticsExportService(statisticsRepository);
    }

    @Nested
    class GenerateCsvStreamResourceFromEntities {

        @Test
        void givenServiceReturnsQueueEntities_whenMethodCalled_thenReturnsInputStreamWithQueueEntitiesAndColumnsAsContent() throws IOException {

            //given
            final List<String> expectedColumns = asList(queueStatisticsExportService.getCOLUMNS());
            final List<StatisticsEntity> entities = asList(createQueueEntityDto(), createQueueEntityDto(), createQueueEntityDto());
            given(statisticsRepository.findAll()).willReturn(entities);

            //when
            final InputStreamResource result = queueStatisticsExportService.generateCsvStreamResourceFromEntities();

            //then

            //CSV document contains all returned entities + a header line
            final List<List<String>> actualContent = convertBytesToString2dArray(result.getInputStream().readAllBytes());
            assertEquals(entities.size() + 1, actualContent.size());

            //CSV headers are the same as defined in the service class
            final List<String> actualColumns = actualContent.get(0); //first line should always be columns
            assertEquals(expectedColumns, actualColumns);

            //Each subsequent line only contains the specified amount of columns, and their values equal that of the entities returned from the service
            for (int i = 1; i < actualContent.size(); i++) {
                final List<String> entityContent = actualContent.get(i);
                System.out.println(entityContent);
                System.out.println(actualColumns);
                assertEquals(actualColumns.size(), entityContent.size());

                final StatisticsEntity entity = entities.get(i - 1);
                assertContentLineAndEntityEqual(entityContent, entity);
            }

        }

        @Test
        void givenServiceReturnsEmptyList_whenMethodCalled_thenReturnsInputStreamWithOnlyColumns() throws IOException {

            //given
            final List<String> expectedColumns = asList(queueStatisticsExportService.getCOLUMNS());
            given(statisticsRepository.findAll()).willReturn(new ArrayList<>());

            //when
            final InputStreamResource result = queueStatisticsExportService.generateCsvStreamResourceFromEntities();

            //then

            //CSV document contains all returned entities + a header line
            final List<List<String>> actualContent = convertBytesToString2dArray(result.getInputStream().readAllBytes());
            assertEquals(1, actualContent.size());

            //CSV headers are the same as defined in the service class
            final List<String> actualColumns = actualContent.get(0); //first line should always be columns
            assertEquals(expectedColumns, actualColumns);
        }

        private void assertContentLineAndEntityEqual(final List<String> contentLine, final StatisticsEntity entity) {
            assertTrue(contentLine.contains(entity.getSubject().getName()));
            assertTrue(contentLine.contains(String.valueOf(entity.getStudyYear())));
            assertTrue(contentLine.contains(String.valueOf(entity.getCreatedDate())));
        }
    }

    private StatisticsEntity createQueueEntityDto() {
        final Random random = new Random();

        final StatisticsEntity statistics =  StatisticsEntity.builder()
                .subject(new Subject("Programmering", ESemester.AUTUMN))
                .placement(new Placement("Datatorget", 1))
                .studyYear(random.nextInt())
                .build();

        statistics.setCreatedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        return statistics;
    }
}