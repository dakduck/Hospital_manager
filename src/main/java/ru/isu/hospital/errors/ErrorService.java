package ru.isu.hospital.errors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import ru.isu.hospital.repository.DoctorRepository;
import ru.isu.hospital.repository.PatientRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Контроль ошибок
 */
@Component("errorService")
public class ErrorService implements HandlerExceptionResolver {
    @Autowired
    DoctorRepository dr;

    @Autowired
    PatientRepository pr;

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView mod = new ModelAndView();
        mod.setViewName("error");
        return mod;
    }
}
