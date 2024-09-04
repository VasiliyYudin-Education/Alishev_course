package ru.alishev.springcourse.DAO;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.models.Person;
import java.util.List;

@Component
public class PersonDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> index(){
        Session session = sessionFactory.getCurrentSession();
        List<Person> people = session.createQuery("select p from Person p", Person.class).list();
        return people;
    }

    @Transactional(readOnly = true)
    public Person show(int id){
        Session session = sessionFactory.getCurrentSession();
        Person person = session.createQuery("select p from Person p where id=:id", Person.class)
                .setParameter("id",id).list().get(0);
        return person;
    }

    @Transactional
    public void save(Person person){
        Session session = sessionFactory.getCurrentSession();
        session.persist(person);
    }

    @Transactional
    public void edit(int id, Person updatedPerson){
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        person.updateByOtherPerson(updatedPerson);
    }

    @Transactional
    public void remove(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.createMutationQuery("delete from Person p where p.id=:id").setParameter("id",id).executeUpdate();
    }

    @Transactional(readOnly = true)
    public Person show(String email){
        Session session = sessionFactory.getCurrentSession();
        List<Person> people = session.createQuery("select p from Person p where p.email=:email", Person.class)
                .setParameter("email",email).getResultList();

        return people.isEmpty()?null:people.get(0);
    }
}
