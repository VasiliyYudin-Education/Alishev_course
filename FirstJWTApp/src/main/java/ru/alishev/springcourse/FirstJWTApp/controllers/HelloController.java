package ru.alishev.springcourse.FirstJWTApp.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.alishev.springcourse.FirstJWTApp.dto.PersonDTO;
import ru.alishev.springcourse.FirstJWTApp.models.Person;
import ru.alishev.springcourse.FirstJWTApp.security.PersonDetails;
import ru.alishev.springcourse.FirstJWTApp.services.AdminService;

@Controller
public class HelloController {
    private final AdminService adminService;
    private final ModelMapper modelMapper;

    @Autowired
    public HelloController(AdminService adminService, ModelMapper modelMapper) {
        this.adminService = adminService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @ResponseBody
    @GetMapping("/user_details")
    public PersonDTO userDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        return convertUserToPersonDTO(personDetails.getPerson());
    }

    @GetMapping("/admin")
    public String admin() {
        adminService.doAdminStuff();
        return "admin_page";
    }

    public PersonDTO convertUserToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }
}
