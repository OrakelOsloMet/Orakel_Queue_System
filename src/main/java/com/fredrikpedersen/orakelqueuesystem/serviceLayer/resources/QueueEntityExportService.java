package com.fredrikpedersen.orakelqueuesystem.serviceLayer.resources;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.QueueEntity;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

/**
 * @author Fredrik Pedersen
 * @since 28/01/2021 at 16:07
 */

@Slf4j
@Service
public class QueueEntityExportService implements DataExportService<QueueEntity> {
    private final String[] COLUMNS = {"id", "subject", "studyYear", "digitalConsultation", "timeConfirmedDone"};


    /**
     *
     * Utilizes OpenCSV to write QueueEntities to a csv file.
     * Column headers are appended manually due to OpenCSV refusing to add those whenever a mapping strategy is applied
     * to filter out which data attributes are written to the CSV-file.
     *
     * @param entities to be written to csv-file
     * @param path to file
     */
    @Override
    public void writeEntityToCsv(List<QueueEntity> entities, String path) {

        try {
            Writer writer = new FileWriter(path);
            writer.append(Arrays.toString(COLUMNS).replace("[", "").replace("]", ""));
            writer.append("\n");

            StatefulBeanToCsv<QueueEntity> beanToCsv = new StatefulBeanToCsvBuilder<QueueEntity>(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withMappingStrategy(createMappingStrategy())
                    .build();

            beanToCsv.write(entities);
            writer.close();

        } catch (IOException e) {
            log.error("Something went wrong while initializing Filewriter in " + getClass().getName());
            e.printStackTrace();
        } catch (CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            log.error("Something went wrong while writing to CSV");
            e.printStackTrace();
        }
    }

    private MappingStrategy<QueueEntity> createMappingStrategy() {
        ColumnPositionMappingStrategy<QueueEntity> mappingStrategy = new ColumnPositionMappingStrategy<>();
        mappingStrategy.setType(QueueEntity.class);
        mappingStrategy.setColumnMapping(COLUMNS);
        return mappingStrategy;
    }
}
