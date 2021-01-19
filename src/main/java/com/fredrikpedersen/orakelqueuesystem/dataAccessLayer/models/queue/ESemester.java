package com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue;

public enum ESemester {

    SPRING("Spring"),
    AUTUMN("Autumn");

    public final String label;

    ESemester(final String label) {
        this.label = label;
    }
}
