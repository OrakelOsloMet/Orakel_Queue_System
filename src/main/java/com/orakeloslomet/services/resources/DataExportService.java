package com.orakeloslomet.services.resources;

import com.orakeloslomet.dtos.DTO;
import org.springframework.core.io.InputStreamResource;

/**
 * @author Fredrik Pedersen
 * @version 1.1
 * @since 26/10/2021 at 17:39
 */

public interface DataExportService<T extends DTO> {

    InputStreamResource generateCsvStreamResourceFromEntities();
}
