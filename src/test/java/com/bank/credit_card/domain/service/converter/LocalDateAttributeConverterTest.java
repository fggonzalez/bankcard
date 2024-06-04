package com.bank.credit_card.domain.service.converter;

import com.bank.credit_card.domain.converter.LocalDateAttributeConverter;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LocalDateAttributeConverterTest {

    private final LocalDateAttributeConverter converter = new LocalDateAttributeConverter();

    @Test
    void testConvertToDatabaseColumn() {
        LocalDate localDate = LocalDate.of(2024, 6, 3);
        Timestamp expectedTimestamp = Timestamp.valueOf(localDate.atStartOfDay());

        Timestamp actualTimestamp = converter.convertToDatabaseColumn(localDate);

        assertEquals(expectedTimestamp, actualTimestamp, "La conversión de LocalDate a Timestamp debería ser correcta");
    }

    @Test
    void testConvertToDatabaseColumn_Null() {
        LocalDate localDate = null;

        Timestamp actualTimestamp = converter.convertToDatabaseColumn(localDate);

        assertNull(actualTimestamp, "La conversión de null LocalDate a Timestamp debería devolver null");
    }

    @Test
    void testConvertToEntityAttribute() {
        Timestamp timestamp = Timestamp.valueOf("2024-06-03 00:00:00");
        LocalDate expectedLocalDate = LocalDate.of(2024, 6, 3);

        LocalDate actualLocalDate = converter.convertToEntityAttribute(timestamp);

        assertEquals(expectedLocalDate, actualLocalDate, "La conversión de Timestamp a LocalDate debería ser correcta");
    }

    @Test
    void testConvertToEntityAttribute_Null() {
        Timestamp timestamp = null;

        LocalDate actualLocalDate = converter.convertToEntityAttribute(timestamp);

        assertNull(actualLocalDate, "La conversión de null Timestamp a LocalDate debería devolver null");
    }
}
