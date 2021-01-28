package com.fredrikpedersen.orakelqueuesystem.serviceLayer.resources;

import java.util.List;

/**
 * @author Fredrik Pedersen
 * @since 28/01/2021 at 17:18
 */
public interface DataExportService<T> {

    void writeEntityToCsv(List<T> entities, String path);
}
