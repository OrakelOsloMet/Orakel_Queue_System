package com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue;

public enum ESubject {
    DATABASES("Databaser"),
    WEBPROGRAMING("Webprogrammering"),
    IOT("Internet of Things"),
    MATHETMATICS("Matte 1000"),
    VISUALIZING("Visualisering"),
    TESTING("Testing"),
    PHYSICS("Fysikk og Kjemi"),
    NETWORKS("Datanettverk og Skytjenester"),
    OS("Operativsystemer"),
    OTHER("Annet");
    
    public final String label;

    ESubject(final String label) {
        this.label = label;
    }
}
