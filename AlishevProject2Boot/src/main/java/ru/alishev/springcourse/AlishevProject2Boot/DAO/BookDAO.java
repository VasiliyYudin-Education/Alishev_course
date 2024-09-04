package ru.alishev.springcourse.AlishevProject2Boot.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.AlishevProject2Boot.Models.Book;
import ru.alishev.springcourse.AlishevProject2Boot.Models.Person;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class BookDAO {

    @PersistenceContext
    private EntityManager entityManager;

    //index
    public List<Book> getSortedBooksPage(int firstBookOfPageNumber, int booksPerPage) {
        
        return entityManager.createQuery("from Book order by release_data", Book.class)
                .setFirstResult(firstBookOfPageNumber).setMaxResults(booksPerPage).getResultList();
    }

    public List<Book> getBooksPage(int firstBookOfPageNumber, int booksPerPage) {
        
        return entityManager.createQuery("from Book", Book.class)
                .setFirstResult(firstBookOfPageNumber).setMaxResults(booksPerPage).getResultList();
    }

    public List<Book> getSortedBooks() {
        
        return entityManager.createQuery("from Book order by release_data", Book.class).getResultList();
    }


    public List<Book> getAllBooks() {
        
        return entityManager.createQuery("from Book", Book.class).getResultList();
    }

    //search
    public List<Book> getSortedBooksSearchPage(String search, int firstBookOfPageNumber, int booksPerPage) {
        
        return entityManager.createQuery("from Book where title like :search order by release_data", Book.class)
                .setParameter("search", search+"%")
                .setFirstResult(firstBookOfPageNumber).setMaxResults(booksPerPage).getResultList();
    }

    public List<Book> getBooksSearchPage(String search, int firstBookOfPageNumber, int booksPerPage) {
        
        return entityManager.createQuery("from Book where title like :search", Book.class)
                .setParameter("search", search+"%")
                .setFirstResult(firstBookOfPageNumber).setMaxResults(booksPerPage).getResultList();
    }

    public List<Book> getSortedBooksSearch(String search) {
        
        return entityManager.createQuery("from Book where title like :search order by release_data", Book.class)
                .setParameter("search", search+"%")
                .getResultList();
    }


    public List<Book> getAllBooksSearch(String search) {
        
        return entityManager.createQuery("from Book where title like :search", Book.class)
                .setParameter("search", search+"%")
                .getResultList();
    }

    public Book getBookById(int id) {
        
        return entityManager.find(Book.class, id);
    }

    @Transactional
    public Book addBook(Book book) {
        
        entityManager.persist(book);
        return book;
    }

    public Optional<Person> getOwner(int id) {
        
        Person owner=entityManager.find(Book.class, id).getOwner();
        if(owner != null){
            return Optional.of(owner);
        }
        else{
            return Optional.empty();
        }
    }

    @Transactional
    public void releaseBook(int id) {
        
        Book book = entityManager.find(Book.class, id);
        book.setOwner(null);
    }

    @Transactional
    public void setPerson(int id, Person person) {
        
        Book book = entityManager.find(Book.class, id);
        book.setOwner(person);
    }

    @Transactional
    public void updateBook(int id, Book updatedBook) {
        
        Book bookToUpdate = entityManager.find(Book.class, id);
        bookToUpdate.updateByOtherBookObj(updatedBook);
    }

    @Transactional
    public void deleteBook(int id) {
        
        entityManager.remove(entityManager.find(Book.class, id));
    }
}
