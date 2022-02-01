package com.orakeloslomet.services.resources;

import com.orakeloslomet.persistance.models.PersistableEntity;
import org.springframework.core.io.InputStreamResource;

/**
 * @author Fredrik Pedersen
 * @version 1.2
 * @since 01/02/2022 at 15:40
 */

public interface DataExportService {

    InputStreamResource generateCsvStreamResourceFromEntities();
}
