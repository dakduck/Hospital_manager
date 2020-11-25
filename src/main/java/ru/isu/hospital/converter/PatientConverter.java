package ru.isu.hospital.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import ru.isu.hospital.model.Patient;
import ru.isu.hospital.repository.PatientRepository;
import ru.isu.hospital.service.MainService;

import java.text.ParseException;



public class PatientConverter implements Converter<String, Patient> {

    @Autowired
    PatientRepository ser;

    public Patient convert(String s) {
        return ser.findById(Long.parseLong(s));
    }

}
