package org.api.parsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeParseException;

import static org.api.parsers.DateParser.parseDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DateParserTest {

    private DateParser dateParser;

    @BeforeEach
    void beforeEach() {
        dateParser = new DateParser();
    }

    @Test
    public void parseDateWhenValidDateShouldBeSuccessful() {
        assertEquals(
                java.time.LocalDate.parse("2022-12-04"),
                parseDate("2022-12-04")
        );
    }

    @Test
    public void parseDateWhenInvalidDateShouldThrow() {
        assertThrows(DateTimeParseException.class, () -> parseDate("2022-13-04"));
    }
}