package com.orakeloslomet.persistance.models.queue;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Month;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Nested
    class fromString {

        @Test
        void returnsExcpectedForAllValidSemesters() {
            final List<String> allSemesterNames = Arrays.stream(ESemester.values()).map(Enum::name).collect(Collectors.toList());

            allSemesterNames.forEach(semesterName -> {
                final ESemester actualResult = ESemester.fromString(semesterName);
                assertEquals(semesterName, actualResult.name());
            });
        }

        @Test
        void invalidNameThrowsNosuchElementException() {
            assertThrows(NoSuchElementException.class, () -> ESemester.fromString("INVALID"));
        }
    }
}