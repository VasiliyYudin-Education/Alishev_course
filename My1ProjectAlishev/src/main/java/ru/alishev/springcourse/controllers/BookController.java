package ru.alishev.springcourse.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.DAO.BookDAO;
import ru.alishev.springcourse.DAO.PersonDAO;
import ru.alishev.springcourse.Models.Book;
import ru.alishev.springcourse.Models.Person;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String getBooks(Model model) {
        model.addAttribute("books", bookDAO.getAllBooks());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String getBook(Model model, @PathVariable("id") int id, @ModelAttribute("person") Person person) {
        model.addAttribute("book",bookDAO.getBookById(id));
        Optional<Person> owner=bookDAO.getHolder(id);
        if(owner.isPresent()) {
            model.addAttribute("owner",owner.get());
        }
        else{
            model.addAttribute("people",personDAO.getAllPersons());
        }
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String addBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        int bookId=bookDAO.addBook(book);
        return "redirect:/books/"+bookId;
    }

    @GetMapping("/{id}/edit")
    public String editBook(Model model,@PathVariable("id") int id) {
        model.addAttribute(bookDAO.getBookById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        bookDAO.updateBook(id, book);
        return "redirect:/books/"+id;
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id) {
        bookDAO.releaseBook(id);
        return "redirect:/books/"+id;
    }

    @PatchMapping("/{id}/add_owner")
    public String addOwner(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookDAO.setPerson(id, person);
        return "redirect:/books/"+id;
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }

}
