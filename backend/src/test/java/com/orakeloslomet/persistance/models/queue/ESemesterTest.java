package com.orakeloslomet.persistance.models.queue;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

class ESemesterTest {

    @Nested
    class currentSemester {

        @Test
        void returnsCorrectSemesterAutumn() {
            final EnumSet<Month> autumnMonths = EnumSet.of(Month.AUGUST, Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER);

            autumnMonths.forEach(month -> {
                final ESemester actualResult = ESemester.currentSemester(month);
                assertEquals(ESemester.AUTUMN, actualResult);
            });
        }

        @Test
        void returnsCorrectSemesterSpring() {
            final EnumSet<Month> springMonths = EnumSet.of(Month.JANUARY, Month.FEBRUARY, Month.MARCH, Month.APRIL, Month.MAY, Month.JUNE, Month.JULY);

            springMonths.forEach(month -> {
                final ESemester actualResult = ESemester.currentSemester(month);
                assertEquals(ESemester.SPRING, actualResult);
            });
        }
    }
}