package com.fredrikpedersen.orakelqueuesystem.serviceLayer.resources;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.QueueEntity;
import com.fredrikpedersen.orakelqueuesystem.dto.QueueEntityDTO;
import com.fredrikpedersen.orakelqueuesystem.serviceLayer.queue.QueueEntityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * @author Fredrik Pedersen
 * @since 28/01/2021 at 16:07
 */

@Slf4j
@Service
public class QueueEntityExportService implements DataExportService<QueueEntityDTO> {

    private final String[] COLUMNS = {"subject", "studyYear", "digitalConsultation", "timeConfirmedDone"};

    private final QueueEntityService queueEntityService;

    public QueueEntityExportService(final QueueEntityService queueEntityService) {
        this.queueEntityService = queueEntityService;
    }

    public InputStreamResource generateCsvStreamResourceFromEntities() {
        List<QueueEntityDTO> entities = queueEntityService.findAllDone();
        return new InputStreamResource(convertEntitiesToCsvBytes(entities));
    }

    public ByteArrayInputStream convertEntitiesToCsvBytes(List<QueueEntityDTO> entities) {

        final CSVFormat format = CSVFormat
                .DEFAULT
                .withQuoteMode(QuoteMode.MINIMAL)
                .withHeader(COLUMNS);

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(outputStream), format);

            for (QueueEntityDTO entity : entities) {
                List<String> data = Arrays.asList(
                        entity.getSubject(),
                        String.valueOf(entity.getStudyYear()),
                        String.valueOf(entity.isDigitalConsultation()),
                        String.valueOf(entity.getTimeConfirmedDone())
                );

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to export data to CSV-file: " + e.getMessage());
        }
    }

}
