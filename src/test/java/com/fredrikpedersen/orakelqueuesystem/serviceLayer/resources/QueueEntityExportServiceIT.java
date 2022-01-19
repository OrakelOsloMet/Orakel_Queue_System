package com.fredrikpedersen.orakelqueuesystem.serviceLayer.resources;

import com.fredrikpedersen.orakelqueuesystem.dtos.QueueEntityDTO;
import com.fredrikpedersen.orakelqueuesystem.serviceLayer.queue.QueueEntityService;
import com.fredrikpedersen.orakelqueuesystem.utilities.constants.Profiles;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Class relies on entities beeing seed in DataSeeder.
 * Should be updated to create it's own entities or atleast muddy the context before writing more tests.
 *
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 26/10/2021 at 18:40
 */

@SpringBootTest
@ActiveProfiles(Profiles.TEST_PROFILE)
public class QueueEntityExportServiceIT extends CSVParsingTestBase {

    @Autowired
    private QueueEntityService queueEntityService;

    @Autowired
    private QueueEntityExportService queueEntityExportService;

    @Nested
    class GenerateCsvStreamResourceFromEntities {

        @Test
        void givenServiceReturnsQueueEntities_whenMethodCalled_thenReturnsInputStreamWithQueueEntitiesAndColumnsAsContent() throws IOException {

            //given
            final List<String> expectedColumns = asList(queueEntityExportService.getCOLUMNS());
            final List<QueueEntityDTO> queueEntities = queueEntityService.findAllDone();

            //when
            final InputStreamResource result = queueEntityExportService.generateCsvStreamResourceFromEntities();

            //then

            //CSV document contains all returned entities + a header line
            final List<List<String>> actualContent = convertBytesToString2dArray(result.getInputStream().readAllBytes());
            System.out.println(actualContent);
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

        private void assertContentLineAndEntityEqual(final List<String> contentLine, final QueueEntityDTO queueEntityDTO) {
            assertTrue(contentLine.contains(queueEntityDTO.getSubject()));
            assertTrue(contentLine.contains(String.valueOf(queueEntityDTO.getStudyYear())));
            assertTrue(contentLine.contains(String.valueOf(queueEntityDTO.isDigitalConsultation())));
            assertTrue(contentLine.contains(String.valueOf(queueEntityDTO.getTimeConfirmedDone())));
        }
    }


}
