package ru.isu.hospital.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter implements Converter<String, Date> {

    public Date convert(String s) {
        Date tmp = null;
        try {
            tmp = new SimpleDateFormat("dd.MM.yyyy").parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tmp;
    }

}
