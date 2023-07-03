package com.Subodh.SchoolProject.controller;

import com.Subodh.SchoolProject.model.Person;
import com.Subodh.SchoolProject.model.Profile;
import com.Subodh.SchoolProject.model.Address;
import com.Subodh.SchoolProject.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.patterns.PerObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("ProfileControllerBean")
@Slf4j
public class ProfileController {

    @Autowired
    PersonRepository personRepository;

    @RequestMapping("/displayProfile")
    public ModelAndView displayMessages(Model model, HttpSession session) {
//        System.out.println("profile IN");
        Person person = (Person) session.getAttribute("loggedInPerson");//getting details from session
        log.info(person.getName());
        Profile profile = new Profile();
        //setting details from session to Profile
        profile.setName(person.getName());
        profile.setMobileNumber(person.getMobileNumber());
        profile.setEmail(person.getEmail());
        //setting details from session to Profile

        //checking if address details are available and assigning to profile
        if (person.getAddress() != null && person.getAddress().getAddressId() > 0) {
            profile.setAddress1(person.getAddress().getAddress1());
            profile.setAddress2(person.getAddress().getAddress2());
            profile.setCity(person.getAddress().getCity());
            profile.setState(person.getAddress().getState());
            profile.setZipCode(person.getAddress().getZipCode());

        }
        ModelAndView modelAndView1 = new ModelAndView("profile.html");
        modelAndView1.addObject("profile", profile);
        System.out.println("profile IN");

        return modelAndView1;
    }

    @RequestMapping("/updateProfile")
    public String updateProfile(@Valid @ModelAttribute("profile") Profile profile, Errors errors, HttpSession session) {
        if (errors.hasErrors()) {
            return "profile.html";
        }
        Person person = (Person) session.getAttribute("loggedInPerson");
        person.setName(profile.getName());
        person.setEmail(profile.getEmail());
        person.setMobileNumber(profile.getMobileNumber());
        if (person.getAddress() == null || !(person.getPersonId() > 0)) {
            person.setAddress(new Address());
        }
        person.getAddress().setAddress1(profile.getAddress1());
        person.getAddress().setAddress2(profile.getAddress2());
        person.getAddress().setState(profile.getState());
        person.getAddress().setCity(profile.getCity());
        person.getAddress().setZipCode(profile.getZipCode());
        personRepository.save(person);
        session.setAttribute("loggedInPerson",person);
        return "redirect:/displayProfile";

    }
}
