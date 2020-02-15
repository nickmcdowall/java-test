package com.nkm.convenience;

import java.time.LocalDate;

import static java.time.LocalDate.now;

public class DescriptiveRelativeDates {

    public static LocalDate nextMonth() {
        return now().plusMonths(1);
    }

    public static LocalDate lastDayOfMonth(LocalDate date) {
        int lengthOfMonth = date.getMonth().length(date.isLeapYear());
        return date.withDayOfMonth(lengthOfMonth);
    }

    public static LocalDate lastDayOfNextMonth() {
        return lastDayOfMonth(now().plusMonths(1));
    }

    public static LocalDate threeDaysHence() {
        return now().plusDays(3);
    }

    public static LocalDate yesterday() {
        return now().minusDays(1);
    }
}
