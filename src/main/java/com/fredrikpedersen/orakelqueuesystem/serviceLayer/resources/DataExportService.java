package com.fredrikpedersen.orakelqueuesystem.serviceLayer.resources;

import com.fredrikpedersen.orakelqueuesystem.dto.DTO;
import org.springframework.core.io.InputStreamResource;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * @author Fredrik Pedersen
 * @since 28/01/2021 at 17:18
 */

public interface DataExportService<T extends DTO> {

    InputStreamResource generateCsvStreamResourceFromEntities();
    ByteArrayInputStream convertEntitiesToCsvBytes(List<T> entities);
}
