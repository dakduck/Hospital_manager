package ru.isu.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.isu.hospital.model.Doctor;
import ru.isu.hospital.model.Patient;
import ru.isu.hospital.repository.DoctorRepository;
import ru.isu.hospital.repository.PatientRepository;
import ru.isu.hospital.service.MainService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/doctors")
public class DoctorsController {
    @Autowired
    MainService service;

    @Autowired
    private DoctorRepository dr;

    @RequestMapping(value="/getalld", method= RequestMethod.GET)
    @ResponseBody
    public List<Doctor> getAllD(){
        return dr.findAllNotAdmin();
    }

    @RequestMapping("/editDoctor")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    // защищает от простого прописывания в строке браузера
    public String editDoctor(Model model, Authentication auth) {
        /*boolean isAdmin =
                auth != null &&
                        auth.getAuthorities().contains(AuthorityUtils.createAuthorityList("ROLE_ADMIN").get(0));
        model.addAttribute("isAdmin", isAdmin);
        if(isAdmin){
            Map<Long,String> users = new HashMap();
            for (Doctor user : this.dr.findAll()) {
                users.put(user.getId(), user.getFirstName().concat(" ").concat(user.getLastName()));
            }
        }*/
        return "editDoctor";
    }

    @RequestMapping("/addDoctor")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    // защищает от простого прописывания в строке браузера
    public String addDoctor(Model model, Authentication auth) {
        return "addDoctor";
    }

    @RequestMapping(value="/searchDoctor")
    public String searchDoctor(Model model,  Authentication auth){
        model.addAttribute("doctors", dr.findAll());
        boolean isAdmin =
                auth != null &&
                        auth.getAuthorities().contains(AuthorityUtils.createAuthorityList("ROLE_ADMIN").get(0));
        model.addAttribute("isAdmin", isAdmin);
        if(isAdmin){
            Map<Long,String> users = new HashMap();
            for (Doctor user : this.dr.findAll()) {
                users.put(user.getId(), user.getFirstName().concat(" ").concat(user.getLastName()));
            }
        }
        return "searchDoctor";
    }

    @RequestMapping(value="/doctor")
    public String getDoctor(
            @RequestParam("doctorId") Long id, Model model, Authentication auth){
        boolean isAdmin = service.isAdmin(auth);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("doctor", dr.findById(id));
        return "doctor";
    }

    @ModelAttribute("sortDoctor")
    public List<String> getSortDoctor() {
        return Arrays.asList("Last Name", "Position", "Username");
    }
}
