package ru.alishev.springcourse.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import ru.alishev.springcourse.Models.Book;
import ru.alishev.springcourse.Models.Person;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class BookDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public BookDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //index
    public List<Book> getSortedBooksPage(int firstBookOfPageNumber, int booksPerPage) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Book order by release_data", Book.class)
                .setFirstResult(firstBookOfPageNumber).setMaxResults(booksPerPage).list();
    }

    public List<Book> getBooksPage(int firstBookOfPageNumber, int booksPerPage) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Book", Book.class)
                .setFirstResult(firstBookOfPageNumber).setMaxResults(booksPerPage).list();
    }

    public List<Book> getSortedBooks() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Book order by release_data", Book.class).list();
    }


    public List<Book> getAllBooks() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Book", Book.class).list();
    }

    //search
    public List<Book> getSortedBooksSearchPage(String search, int firstBookOfPageNumber, int booksPerPage) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Book where title like :search order by release_data", Book.class)
                .setParameter("search", search+"%")
                .setFirstResult(firstBookOfPageNumber).setMaxResults(booksPerPage).list();
    }

    public List<Book> getBooksSearchPage(String search, int firstBookOfPageNumber, int booksPerPage) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Book where title like :search", Book.class)
                .setParameter("search", search+"%")
                .setFirstResult(firstBookOfPageNumber).setMaxResults(booksPerPage).list();
    }

    public List<Book> getSortedBooksSearch(String search) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Book where title like :search order by release_data", Book.class)
                .setParameter("search", search+"%")
                .list();
    }


    public List<Book> getAllBooksSearch(String search) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Book where title like :search", Book.class)
                .setParameter("search", search+"%")
                .list();
    }

    public Book getBookById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, id);
    }

    @Transactional
    public Book addBook(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(book);
        return book;
    }

    public Optional<Person> getOwner(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person owner=session.get(Book.class, id).getOwner();
        if(owner != null){
            return Optional.of(owner);
        }
        else{
            return Optional.empty();
        }
    }

    @Transactional
    public void releaseBook(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        book.setOwner(null);
    }

    @Transactional
    public void setPerson(int id, Person person) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        book.setOwner(person);
    }

    @Transactional
    public void updateBook(int id, Book updatedBook) {
        Session session = sessionFactory.getCurrentSession();
        Book bookToUpdate = session.get(Book.class, id);
        bookToUpdate.updateByOtherBookObj(updatedBook);
    }

    @Transactional
    public void deleteBook(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Book.class, id));
    }
}
