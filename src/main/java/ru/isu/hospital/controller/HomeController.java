package ru.isu.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.isu.hospital.model.Doctor;
import ru.isu.hospital.model.Patient;
import ru.isu.hospital.repository.DoctorRepository;
import ru.isu.hospital.repository.PatientRepository;
import ru.isu.hospital.service.MainService;

import javax.validation.Valid;
import java.util.HashSet;

@Controller
public class HomeController {

    @Autowired
    private DoctorRepository dr;


    @RequestMapping(value="/")
    public String home(){
        return "home";
    }

    @RequestMapping(value="/about")
    public String about(){
        return "about";
    }

    @RequestMapping(value="/login", method= RequestMethod.GET)
    public String goLogin(){
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String goRegister(Model model) {
        Doctor b = new Doctor();
        model.addAttribute("doc", b);
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@Valid @ModelAttribute("doc") Doctor user,
                           BindingResult errors) {
        if(errors.hasErrors()){
            return "register";
        }
        user.setRole("ROLE_USER");
        user.setPatients(new HashSet<>());
        dr.save(user);
        // Сразу авторизовываемся
        //Program authentication
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return "redirect:/about";
    }
}
