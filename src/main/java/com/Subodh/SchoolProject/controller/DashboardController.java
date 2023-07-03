package com.Subodh.SchoolProject.controller;

import com.Subodh.SchoolProject.model.Person;
import com.Subodh.SchoolProject.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class DashboardController {

    @Autowired
    PersonRepository personRepository;

    @RequestMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication, HttpSession session) {
        System.out.println("-------------------------Dashboard IN---------------------");
        Person person = personRepository.readByEmail(authentication.getName()); // for profile data
        log.info(person.getName());
        // System.out.println("/n Person======"+person);

        model.addAttribute("username", person.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        // throw new RuntimeException("IT's been bad day");

        // assigning class attribute
        if (null != person.getSchoolClass() && null != person.getSchoolClass().getName()) {
            model.addAttribute("enrolledClass", person.getSchoolClass().getName());
        }

        session.setAttribute("loggedInPerson", person);
        System.out.println("-------------------------Dashboard OUT---------------------");

        return "dashboard.html";
    }

}