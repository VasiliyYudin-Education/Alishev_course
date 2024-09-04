package ru.alishev.springcourse.FirstJWTApp.controllers;


import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.FirstJWTApp.dto.AuthenticationDTO;
import ru.alishev.springcourse.FirstJWTApp.dto.PersonDTO;
import ru.alishev.springcourse.FirstJWTApp.dto.PersonErrorResponse;
import ru.alishev.springcourse.FirstJWTApp.exceptions.PersonValidationException;
import ru.alishev.springcourse.FirstJWTApp.models.Person;
import ru.alishev.springcourse.FirstJWTApp.security.JWTUtil;
import ru.alishev.springcourse.FirstJWTApp.services.RegistrationService;
import ru.alishev.springcourse.FirstJWTApp.util.PersonValidator;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final PersonValidator personValidator;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(RegistrationService registrationService, PersonValidator personValidator, PasswordEncoder passwordEncoder, JWTUtil jwtUtil, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.registrationService = registrationService;
        this.personValidator = personValidator;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public Map<String,String> performRegistration(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {
        Person person = convertToPerson(personDTO);
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errors.append(error.getObjectName())
                        .append("--")
                        .append(error.getDefaultMessage())
                        .append(";\n");
            }
            throw new PersonValidationException(errors.toString());
        }
        registrationService.register(person);
        return Map.of("jwtToken",jwtUtil.generateToken(person.getUsername()));
    }

    @PostMapping("/login")
    public Map<String,String> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        authenticationDTO.getUsername(),
                        authenticationDTO.getPassword());
        try{
            authenticationManager.authenticate(authenticationToken);
        }
        catch(AuthenticationException e) {
            return Map.of("message", "Incorrect credentials!");
        }
        String token = jwtUtil.generateToken(authenticationDTO.getUsername());
        return Map.of("jwtToken", token);
    }

    @ExceptionHandler
    public ResponseEntity<PersonErrorResponse> handlePersonException(PersonValidationException e) {
        PersonErrorResponse response = new PersonErrorResponse();
        response.setErrorMessage(e.getMessage());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    public Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }
}
