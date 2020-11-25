package ru.isu.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.isu.hospital.model.Doctor;
import ru.isu.hospital.model.Patient;
import ru.isu.hospital.model.Visit;
import ru.isu.hospital.repository.DoctorRepository;
import ru.isu.hospital.repository.PatientRepository;
import ru.isu.hospital.repository.VisitRepository;
import ru.isu.hospital.service.MainService;
import ru.isu.hospital.service.SearchService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping(value="/patients")
public class PatientsController {
    @Autowired
    MainService service;

    @Autowired
    private PatientRepository pr;

    @Autowired
    private DoctorRepository dr;

    @Autowired
    private VisitRepository vr;

    @RequestMapping(value="/getall", method= RequestMethod.GET)
    @ResponseBody
    public List<Patient> getAll(){
        return pr.findAll();
    }

    @ModelAttribute("sortPatient")
    public List<String> getSortPatient() {
        return Arrays.asList("Last Name", "Age");
    }

    @RequestMapping("/searchPatient")
    // защищает от простого прописывания в строке браузера
    public String editPatient(Model model, Authentication auth) {
        boolean isAdmin = service.isAdmin(auth);
        model.addAttribute("isAdmin", isAdmin);
        return "searchPatient";
    }

    @ResponseBody
    @RequestMapping(value = "/patientSort", method = RequestMethod.POST, consumes = "application/json")
    public List<Patient> saveObj(Model model, HttpServletRequest request, HttpServletResponse response, @RequestBody SearchService searchService) {
        return pr.byLastName(new PageRequest(searchService.getPage(), 5, new Sort(Sort.Direction.DESC, searchService.getSort().toLowerCase())));
    }

    @ResponseBody
    @RequestMapping(value = "/getallP", method = RequestMethod.POST, consumes = "application/json")
    public List<Patient>  getAll(Model model, HttpServletRequest request, HttpServletResponse response, @RequestBody SearchService searchService) {
        System.out.println(
                pr.byLastName()
        );
        return pr.byLastName();
    }

    @RequestMapping(value="/addPatient",method= RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addPatient(Model model){
        Patient b = new Patient();
        model.addAttribute("pat", b);
        return "addPatient";
    }

    @RequestMapping(value="/addPatient",method= RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String savePatient(
            @Valid @ModelAttribute("pat") Patient p,
            BindingResult errors,
            Model model){
        if(errors.hasErrors()){
            return "addPatient";
        }
        p.setId((long) 123);
        p.setAge(service.doAge(p.getBd()));
        p.setVisits(new HashSet<>());
        System.out.println(p);
        pr.save(p);
        return "redirect:/patients/searchPatient";
    }

    @RequestMapping(value="/addVisit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addVisit(@RequestParam("patientId") Long id, Model model){
        Visit b = new Visit();
        model.addAttribute("visit", b);
        model.addAttribute("patId", id);
        return "addVisit";
    }

    @RequestMapping(value="/addVisit",method= RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveVisit(
            @Valid @ModelAttribute("visit") Visit p,
            @RequestParam("patientId") Long id,
            BindingResult errors,
            Model model){
        if(errors.hasErrors()){
            return "searchPatient";
        }
        p.setId((long) 123);
        vr.save(p);
        Patient pat = pr.findById(id);
        pat.add(p);
        pr.save(pat);
        return "redirect:/patients/searchPatient";
    }

 /*   @RequestMapping(value="/patient")
    public String getPatient(
            @RequestParam("patientId") Long id, Model model, Authentication auth, Pageable page, Sort sort){
        boolean isAdmin = service.isAdmin(auth);
            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("id", id);
            model.addAttribute("patient", pr.findById(id));
        Pageable pageNew = new PageRequest(page.getPageNumber(), 2, page.getSort());
        Sort.Order o = null;
        if (sort != null)
            o = sort.iterator().next();
        model.addAttribute("sort", (sort != null ? o.getProperty() : ""));
        model.addAttribute("dir", (sort != null ? o.getDirection() : ""));
        model.addAttribute("visits", pr.findVisits(id, pageNew));
        return "patient";
    }*/


 // *** Получение пациента с использованием конвертера ***
    @RequestMapping(value="/patient/{patientId}")
    public String getPatient(
            @PathVariable("patientId") Patient patient, Model model, Authentication auth, Pageable page, Sort sort){
        System.out.println("++++++++++++++++++++++++++++++" + patient);
        System.out.println("------------------------------" + patient.getVisits().toString());
        boolean isAdmin = service.isAdmin(auth);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("patient", patient);

        Pageable pageNew = new PageRequest(page.getPageNumber(), 2, page.getSort());
        Sort.Order o = null;
        if (sort != null)
            o = sort.iterator().next();
        model.addAttribute("sort", (sort != null ? o.getProperty() : ""));
        model.addAttribute("dir", (sort != null ? o.getDirection() : ""));
        model.addAttribute("visits", pr.findVisits(patient.getId(), pageNew));
        return "patient";
    }

}
