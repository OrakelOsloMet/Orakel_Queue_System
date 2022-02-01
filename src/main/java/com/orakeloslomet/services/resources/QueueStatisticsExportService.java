package com.orakeloslomet.services.resources;

import com.orakeloslomet.persistance.models.statistics.StatisticsEntity;
import com.orakeloslomet.persistance.repositories.StatisticsRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author Fredrik Pedersen
 * @since 01/02/2022 at 15:34
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class QueueStatisticsExportService implements DataExportService {

    @Getter
    private final String[] COLUMNS = {"subject", "studyYear", "digitalConsultation", "timeConfirmedDone"};

    private final StatisticsRepository statisticsRepository;

    public InputStreamResource generateCsvStreamResourceFromEntities() throws RuntimeException {
        final List<StatisticsEntity> entities = statisticsRepository.findAll();
        return new InputStreamResource(convertEntitiesToCsvBytes(entities));
    }

    private ByteArrayInputStream convertEntitiesToCsvBytes(final List<StatisticsEntity> entities) {

        final CSVFormat format = CSVFormat
                .DEFAULT
                .withQuoteMode(QuoteMode.MINIMAL)
                .withHeader(COLUMNS);

        try {
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(outputStream), format);

            for (StatisticsEntity entity : entities) {
                final List<String> data = asList(
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
            throw new RuntimeException("Failed to export data to CSV-file: " + e.getMessage());
        }
    }

}
