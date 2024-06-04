package com.bank.credit_card.domain.service.converter;

import com.bank.credit_card.domain.converter.DateFormatter;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;



class DateFormatterTest {

    @Test
    void testFormatDate() {
        LocalDate date = LocalDate.of(2024, 5, 15);  // Fecha específica para probar
        String expectedFormattedDate = "05/2024";
        String actualFormattedDate = DateFormatter.formatDate(date);

        assertEquals(expectedFormattedDate, actualFormattedDate, "El formato de la fecha debería ser MM/yyyy");
    }

    @Test
    void testFormatDateWithDifferentMonth() {
        LocalDate date = LocalDate.of(2024, 12, 1);  // Otra fecha específica para probar
        String expectedFormattedDate = "12/2024";
        String actualFormattedDate = DateFormatter.formatDate(date);

        assertEquals(expectedFormattedDate, actualFormattedDate, "El formato de la fecha debería ser MM/yyyy");
    }

    @Test
    void testFormatCurrentDate() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        String expectedFormattedDate = date.format(formatter);
        String actualFormattedDate = DateFormatter.formatDate(date);

        assertEquals(expectedFormattedDate, actualFormattedDate, "El formato de la fecha debería ser MM/yyyy para la fecha actual");
    }


}
