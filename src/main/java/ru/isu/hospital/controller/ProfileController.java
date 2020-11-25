package ru.isu.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.isu.hospital.model.Doctor;
import ru.isu.hospital.model.Patient;
import ru.isu.hospital.repository.DoctorRepository;
import ru.isu.hospital.repository.PatientRepository;
import ru.isu.hospital.service.MainService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(value="/log")
public class ProfileController {
    @Autowired
    MainService service;

    @Autowired
    private PatientRepository pr;

    @Autowired
    private DoctorRepository dr;


    @RequestMapping(value="/", method= RequestMethod.GET)
    public String getProfile(Model model, Authentication auth){
        boolean isAdmin = service.isAdmin(auth);
        Doctor user = (Doctor) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("doctor",user);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("myP", user.getPatients());
        return "profile";
    }

    @RequestMapping(value="/getMyP", method= RequestMethod.GET)
    @ResponseBody
    public Set<Patient> getMyP(){
        Doctor user = (Doctor) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getPatients();
    }


    @RequestMapping(value="/editDoctor")
    @PreAuthorize("hasRole('ADMIN')")
    public String editDoctor(){
        return "editDoctor";
    }


    // *** Отлавливаем ошибки ***
    @RequestMapping(value="/errors", method = RequestMethod.GET)
    public ModelAndView errorPage(HttpServletRequest httpRequest) {
        int errorCode = (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");

        ModelAndView errorPage = new ModelAndView("error");
        String errorMsg = "Error "+ errorCode;
        if (errorCode==404) {
            errorMsg = "Error "+ errorCode + "\nThis page doesn't exist.";
        } else if (errorCode==500) {
            errorMsg = errorMsg + "\nThe server problem. We are sorry.";
        }
        errorPage.addObject("errorMsg", errorMsg);
        return errorPage;
    }

}
