package com.orakeloslomet.persistance.models.queue;

import java.time.Month;
import java.util.EnumSet;

public enum ESemester {

    SPRING("Spring", EnumSet.of(Month.JANUARY, Month.FEBRUARY, Month.MARCH, Month.APRIL, Month.MAY, Month.JUNE, Month.JULY)),
    AUTUMN("Autumn", EnumSet.of(Month.AUGUST, Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER));

    public final String label;
    public final EnumSet<Month> semesterMonths;

    ESemester(final String label, final EnumSet<Month> semesterMonths) {
        this.label = label;
        this.semesterMonths = semesterMonths;
    }

    public static ESemester currentSemester(final Month currentMonth) {
        if (ESemester.SPRING.semesterMonths.contains(currentMonth)) {
            return ESemester.SPRING;
        }

        return ESemester.AUTUMN;
    }
}
