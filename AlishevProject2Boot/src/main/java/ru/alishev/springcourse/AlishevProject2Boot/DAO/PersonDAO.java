package ru.alishev.springcourse.AlishevProject2Boot.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.AlishevProject2Boot.Models.Book;
import ru.alishev.springcourse.AlishevProject2Boot.Models.Person;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class PersonDAO {
    
    @PersistenceContext
    private EntityManager entityManager;

    public List<Person> getAllPersons() {
        
        return entityManager.createQuery("from Person", Person.class).getResultList();
    }

    public Person getPersonById(int id) {
        
        Person person = entityManager.find(Person.class, id);
        Hibernate.initialize(person.getBooks());
        return person;
    }

    @Transactional
    public Person addPerson(Person person) {
        
        entityManager.persist(person);
        return person;
    }

    @Transactional
    public void updatePerson(int id, Person updatedPerson) {
        
        Person person = entityManager.find(Person.class, id);
        person.updateByOtherPersonObject(updatedPerson);
    }

    @Transactional
    public void deletePerson(int id) {
        
        entityManager.remove(entityManager.find(Person.class, id));
    }

    public List<Book> getAllBooksByOwner(int person_id) {
        
        Person person = entityManager.find(Person.class, person_id);
        Hibernate.initialize(person.getBooks());
        return person.getBooks();
//        return entityManager.createQuery("from Book where owner = :person_id", Book.class)
//                .setParameter("person_id", person_id).getResultList();
    }

    public Optional<Person> getPersonByName(String name) {
        
        Person person = entityManager.createQuery("from Person where name = :name", Person.class)
                .setParameter("name", name).getSingleResult();
        if(person != null){
            return Optional.of(person);
        }
        else{
            return Optional.empty();
        }
    }
}
