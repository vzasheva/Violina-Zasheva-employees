package org.api.parsers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

enum DateFormat {
    MM_DD_YYYY_DATE_FORMAT,
    DD_MM_YYYY_DATE_FORMAT,
    YYYY_MM_DD_DATE_FORMAT
}

public class DateParser {

    // DateTimeFormatterBuilder can receive multiple date patterns and use them all to try to parse a given date
    public static LocalDate parseDate(String date) {
        DateTimeFormatterBuilder dateTimeFormatterBuilder = new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ofPattern("[MM/dd/yyyy]" + "[dd-MM-yyyy]" + "[yyyy-MM-dd]"));
        DateTimeFormatter dateTimeFormatter = dateTimeFormatterBuilder.toFormatter();
        return LocalDate.parse(date, dateTimeFormatter);
    }
}