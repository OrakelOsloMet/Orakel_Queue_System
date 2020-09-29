package com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue;

public enum ESubject {
    PROGRAMMING("Programmering"),
    DISCRETE_MATHEMATICS("Diskret Matte"),
    WEB_DEVELOPMENT("Web Utvikling"),
    ALGORITHMS_AND_DATASTRUCTURES("Alogritmer og Datastrukturer"),
    SOFTWARE_ENGINEERING("Systemutvikling"),
    MACHINE_HUMAN_INTERACTION("MMI"),
    WEB_APPLICATIONS("WebApps");

    public final String label;

    private ESubject(final String label) {
        this.label = label;
    }
}
