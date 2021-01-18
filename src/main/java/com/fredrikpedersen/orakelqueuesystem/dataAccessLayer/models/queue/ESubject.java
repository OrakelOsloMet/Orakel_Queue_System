package com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue;

public enum ESubject {
    PROGRAMMING("Programmering"),
    DISCRETE_MATHEMATICS("Diskret Matte"),
    WEB_DEVELOPMENT("Web Utvikling"),
    ALGORITHMS_AND_DATASTRUCTURES("Alogritmer og Datastrukturer"),
    SOFTWARE_ENGINEERING("Systemutvikling"),
    MACHINE_HUMAN_INTERACTION("MMI"),
    WEB_APPLICATIONS("WebApps"),
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
