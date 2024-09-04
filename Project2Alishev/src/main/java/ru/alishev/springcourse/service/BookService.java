package ru.alishev.springcourse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alishev.springcourse.DAO.BookDAO;
import ru.alishev.springcourse.Models.Book;
import ru.alishev.springcourse.Models.Person;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookDAO bookDAO;

    @Autowired
    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public List<Book> getAllBooks(Integer page, Integer booksPerPage, Boolean sortByYear) {
        boolean pagination = page != null && booksPerPage != null;
        boolean sort = sortByYear != null && sortByYear;
        if(pagination&&sort){
            int first = page * booksPerPage;
            return bookDAO.getSortedBooksPage(first, booksPerPage);
        }
        if(!pagination&&sort){
            return bookDAO.getSortedBooks();
        }
        if (pagination) {
            int first = page * booksPerPage;
            return bookDAO.getBooksPage(first,booksPerPage);
        }
        return bookDAO.getAllBooks();
    }

    public List<Book> searchBooks(String search, Integer page, Integer booksPerPage, Boolean sortByYear) {
        boolean pagination = page != null && booksPerPage != null;
        boolean sort = sortByYear != null && sortByYear;
        if(pagination&&sort){
            int first = page * booksPerPage;
            return bookDAO.getSortedBooksSearchPage(search, first, booksPerPage);
        }
        if(!pagination&&sort){
            return bookDAO.getSortedBooksSearch(search);
        }
        if (pagination) {
            int first = page * booksPerPage;
            return bookDAO.getBooksSearchPage(search, first,booksPerPage);
        }
        return bookDAO.getAllBooksSearch(search);
    }

    public Book getBookById(int id) {
        return bookDAO.getBookById(id);
    }

    public Book addBook(Book book) {
        return bookDAO.addBook(book);
    }

    public Optional<Person> getOwner(int id) {
        return bookDAO.getOwner(id);
    }

    public void updateBook(int id, Book book) {
        bookDAO.updateBook(id, book);
    }

    public void releaseBook(int id) {
        bookDAO.releaseBook(id);
    }

    public void setPerson(int id, Person person) {
        bookDAO.setPerson(id, person);
    }

    public void deleteBook(int id) {
        bookDAO.deleteBook(id);
    }
}
