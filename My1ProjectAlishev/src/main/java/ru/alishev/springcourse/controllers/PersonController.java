package ru.alishev.springcourse.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.DAO.PersonDAO;
import ru.alishev.springcourse.Models.Book;
import ru.alishev.springcourse.Models.Person;

import java.util.List;


@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonDAO personDAO;

    @Autowired
    public PersonController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String getPeople(Model model) {
        model.addAttribute("people",personDAO.getAllPersons());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String getPerson(Model model, @PathVariable("id") int id) {
        Person person = personDAO.getPersonById(id);
        List<Book> books=personDAO.getAllBooks(id);
        model.addAttribute("person",person);
        model.addAttribute("books",books);
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String savePerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                             Model model) {
        if(bindingResult.hasErrors()) {
            return "people/new";
        }
        int personId=personDAO.addPerson(person);
        return "redirect:/people/"+personId;
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable("id") int id) {
        model.addAttribute(personDAO.getPersonById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult, @PathVariable("id") int id) {
        if(bindingResult.hasErrors()) {
            return "people/edit";
        }
        personDAO.updatePerson(id, person);
        return "redirect:/people/"+id;
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personDAO.deletePerson(id);
        return "redirect:/people";
    }

}
