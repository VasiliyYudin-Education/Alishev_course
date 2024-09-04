package ru.alishev.springcourse.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.Models.Book;
import ru.alishev.springcourse.Models.Person;
import ru.alishev.springcourse.service.PersonService;

import java.util.List;


@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public String getPeople(Model model) {
        model.addAttribute("people", personService.getAllPersons());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String getPerson(Model model, @PathVariable("id") int id) {
        Person person = personService.getPersonById(id);
        List<Book> books = personService.getAllBooksByOwner(id);
        model.addAttribute("person", person);
        model.addAttribute("books", books);
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String savePerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        Person savedPerson = personService.addPerson(person);
        return "redirect:/people/" + savedPerson.getPerson_id();
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable("id") int id) {
        model.addAttribute(personService.getPersonById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        personService.updatePerson(id, person);
        return "redirect:/people/" + id;
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personService.deletePerson(id);
        return "redirect:/people";
    }

}
