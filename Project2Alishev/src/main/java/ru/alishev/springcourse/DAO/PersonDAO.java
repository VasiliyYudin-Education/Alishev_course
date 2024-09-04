package ru.alishev.springcourse.DAO;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.Models.Book;
import ru.alishev.springcourse.Models.Person;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class PersonDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Person> getAllPersons() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Person", Person.class).list();
    }

    public Person getPersonById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        Hibernate.initialize(person.getBooks());
        return person;
    }

    @Transactional
    public Person addPerson(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(person);
        return person;
    }

    @Transactional
    public void updatePerson(int id, Person updatedPerson) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        person.updateByOtherPersonObject(updatedPerson);
    }

    @Transactional
    public void deletePerson(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Person.class, id));
    }

    public List<Book> getAllBooksByOwner(int person_id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, person_id);
        Hibernate.initialize(person.getBooks());
        return person.getBooks();
//        return session.createQuery("from Book where owner = :person_id", Book.class)
//                .setParameter("person_id", person_id).list();
    }

    public Optional<Person> getPersonByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.createQuery("from Person where name = :name", Person.class)
                .setParameter("name", name).uniqueResult();
        if(person != null){
            return Optional.of(person);
        }
        else{
            return Optional.empty();
        }
    }
}
