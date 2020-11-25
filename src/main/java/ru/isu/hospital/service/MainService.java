package ru.isu.hospital.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import ru.isu.hospital.model.Doctor;
import ru.isu.hospital.model.Patient;
import ru.isu.hospital.model.Visit;
import ru.isu.hospital.repository.DoctorRepository;
import ru.isu.hospital.repository.PatientRepository;

import javax.print.Doc;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;


@Service("service")
public class MainService {

    @Autowired
    DoctorRepository dr;

    @Autowired
    PatientRepository pr;

    // *** Подсчитываем возраст ***
    public Integer doAge(LocalDate bd) {
        LocalDate now = LocalDate.now();
        Period p = Period.between(bd, now);
        Integer y = p.getYears();
        return y;
    }

    public boolean isAdmin(Authentication auth) {
        return auth != null && auth.getAuthorities().contains(AuthorityUtils.createAuthorityList("ROLE_ADMIN").get(0));
    }

}
