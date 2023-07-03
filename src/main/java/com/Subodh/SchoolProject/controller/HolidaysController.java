package com.Subodh.SchoolProject.controller;

import com.Subodh.SchoolProject.model.Holiday;
import com.Subodh.SchoolProject.repository.HolidaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class HolidaysController {

    @Autowired
    private HolidaysRepository holidaysRepository;

    @GetMapping("/holidays/{display}")
    public String displayHolidays(@PathVariable String display,
                                  Model model) {
        if (null != display && display.equals("all")) {
            model.addAttribute("festival", true);
            model.addAttribute("federal", true);
        } else if (null != display && display.equals("festival")) {
            model.addAttribute("festival", true);

        } else if (null != display && display.equals("federal")) {
            model.addAttribute("federal", true);

        }

//            List<Holiday> holidays = Arrays.asList(
//                    new Holiday("Jan 1", "New Year's Day", Holiday.Type.FESTIVAL),
//                    new Holiday("Oct 31", "Halloween Day", Holiday.Type.FESTIVAL),
//                    new Holiday("Nov 24", "Thanksgiving Day", Holiday.Type.FESTIVAL),
//                    new Holiday("Dec 25", "Christmas Day", Holiday.Type.FESTIVAL),
//                    new Holiday("Jan 17", "Martin Luther King jr Day", Holiday.Type.FEDERAL),
//                    new Holiday("July 4", "Independence Day", Holiday.Type.FEDERAL),
//                    new Holiday("Sep 5", "Labor Day", Holiday.Type.FEDERAL),
//                    new Holiday("Nov 11", "Veterans Day", Holiday.Type.FEDERAL)
//            );

//        List<Holiday> holidays= holidaysRepository.findAllHolidays();
        Iterable<Holiday> holidays = holidaysRepository.findAll();
        List<Holiday> holidayList = StreamSupport.stream(holidays.spliterator(), false).collect(Collectors.toList());
        Holiday.Type[] types = Holiday.Type.values();
        for (Holiday.Type type : types) {
            model.addAttribute(type.toString(),
//                    (holidays.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
                    (holidayList.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
        }
        return "holidays.html";
    }
}
