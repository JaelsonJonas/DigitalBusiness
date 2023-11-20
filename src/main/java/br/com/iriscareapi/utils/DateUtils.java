package br.com.iriscareapi.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateUtils {

    public LocalDate parseString(String date) throws Exception {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            return LocalDate.parse(date, formatter);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error trying parse " + date + " to Calendar entity");
        }
    }

}
