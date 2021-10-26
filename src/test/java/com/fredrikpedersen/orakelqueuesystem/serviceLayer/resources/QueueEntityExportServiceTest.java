package com.fredrikpedersen.orakelqueuesystem.serviceLayer.resources;

import com.fredrikpedersen.orakelqueuesystem.dtos.QueueEntityDTO;
import com.fredrikpedersen.orakelqueuesystem.serviceLayer.queue.QueueEntityService;
import com.fredrikpedersen.orakelqueuesystem.utilities.annotations.Required;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.InputStreamResource;

import java.io.IOException;
import java.util.*;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.BDDMockito.given;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 26/10/2021 at 17:21
 */

@ExtendWith(MockitoExtension.class)
class QueueEntityExportServiceTest {

    @Mock
    private QueueEntityService queueEntityService;

    private QueueEntityExportService queueEntityExportService;

    @BeforeEach
    void setup() {
        queueEntityExportService = new QueueEntityExportService(queueEntityService);
    }

    @Nested
    class GenerateCsvStreamResourceFromEntities {

        @Test
        void givenServiceReturnsQueueEntities_whenMethodCalled_thenReturnsInputStreamWithQueueEntitiesAndColumnsAsContent() throws IOException {

            //given
            final List<String> expectedColumns = asList(queueEntityExportService.getCOLUMNS());
            final List<QueueEntityDTO> queueEntities = asList(createQueueEntityDto(), createQueueEntityDto(), createQueueEntityDto());
            given(queueEntityService.findAllDone()).willReturn(queueEntities);

            //when
            final InputStreamResource result = queueEntityExportService.generateCsvStreamResourceFromEntities();

            //then

            //CSV document contains all returned entities + a header line
            final List<List<String>> actualContent = convertBytesToString2dArray(result.getInputStream().readAllBytes());
            assertEquals(queueEntities.size() + 1, actualContent.size());

            //CSV headers are the same as defined in the service class
            final List<String> actualColumns = actualContent.get(0); //first line should always be columns
            assertEquals(expectedColumns, actualColumns);

            //Each subsequent line only contains the specified amount of columns, and their values equal that of the entities returned from the service
            for (int i = 1; i < actualContent.size(); i++) {
                final List<String> entityContent = actualContent.get(i);
                assertEquals(actualColumns.size(), entityContent.size());

                final QueueEntityDTO queueEntityDTO = queueEntities.get(i - 1);
                assertContentLineAndEntityEqual(entityContent, queueEntityDTO);
            }

        }

        @Test
        void givenServiceReturnsEmptyList_whenMethodCalled_thenReturnsInputStreamWithOnlyColumns() throws IOException {

            //given
            final List<String> expectedColumns = asList(queueEntityExportService.getCOLUMNS());
            given(queueEntityService.findAllDone()).willReturn(new ArrayList<>());

            //when
            final InputStreamResource result = queueEntityExportService.generateCsvStreamResourceFromEntities();

            //then

            //CSV document contains all returned entities + a header line
            final List<List<String>> actualContent = convertBytesToString2dArray(result.getInputStream().readAllBytes());
            assertEquals(1, actualContent.size());

            //CSV headers are the same as defined in the service class
            final List<String> actualColumns = actualContent.get(0); //first line should always be columns
            assertEquals(expectedColumns, actualColumns);
        }
    }

    private QueueEntityDTO createQueueEntityDto() {
        final Random random = new Random();

        return QueueEntityDTO.builder()
                .subject("Programmering")
                .studyYear(random.nextInt())
                .digitalConsultation(random.nextBoolean())
                .timeConfirmedDone(new Date().toString())
                .build();
    }

    private List<List<String>> convertBytesToString2dArray(final byte[] content) {
        final String readable = new String(content);
        final String[] lines = readable.split("\\r?\\n"); //split on new lines

        final List<List<String>> contentLines = new ArrayList<>();
        for (String line : lines) {
            contentLines.add(asList(line.split(",")));
        }

        return contentLines;
    }

    private void assertContentLineAndEntityEqual(final List<String> contentLine, final QueueEntityDTO queueEntityDTO) {
        assertTrue(contentLine.contains(queueEntityDTO.getSubject()));
        assertTrue(contentLine.contains(String.valueOf(queueEntityDTO.getStudyYear())));
        assertTrue(contentLine.contains(String.valueOf(queueEntityDTO.isDigitalConsultation())));
        assertTrue(contentLine.contains(String.valueOf(queueEntityDTO.getTimeConfirmedDone())));
    }
}