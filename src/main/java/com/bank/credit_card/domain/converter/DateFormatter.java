package com.bank.credit_card.domain.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatter {

    public static String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        return date.format(formatter);
    }

    public static void main(String[] args) {
        LocalDate date = LocalDate.now();
        String formattedDate = formatDate(date);
        System.out.println(formattedDate);  // Salida: 05/2024
    }
}
