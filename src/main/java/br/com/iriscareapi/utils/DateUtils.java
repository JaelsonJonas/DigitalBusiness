package br.com.iriscareapi.utils;

import br.com.iriscareapi.exception.DataUtilsException;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@UtilityClass
public class DateUtils {

    public LocalDate parseString(String date) throws Exception {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            return LocalDate.parse(date, formatter);
        } catch (Exception e) {
            throw new DataUtilsException("Error trying parse " + date + " to Localdate entity");
        }
    }

}
