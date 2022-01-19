package com.fredrikpedersen.orakelqueuesystem.serviceLayer.resources;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 26/10/2021 at 18:45
 */

public abstract class CSVParsingTestBase {

    protected List<List<String>> convertBytesToString2dArray(final byte[] content) {
        final String readable = new String(content);
        final String[] lines = readable.split("\\r?\\n"); //split on new lines

        final List<List<String>> contentLines = new ArrayList<>();
        for (String line : lines) {
            contentLines.add(asList(line.split(",")));
        }

        return contentLines;
    }
}
