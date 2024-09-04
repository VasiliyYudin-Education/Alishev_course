package ru.alishev.springcourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.models.Person;
import ru.alishev.springcourse.repositories.PeopleRepos;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepos peopleRepos;

    @Autowired
    public PeopleService(PeopleRepos peopleRepos) {
        this.peopleRepos = peopleRepos;
    }

    public List<Person> findAll() {
        return peopleRepos.findAll();
    }

    public Person findById(int id) {
        return peopleRepos.findById(id).orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepos.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepos.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepos.deleteById(id);
    }

    public Person findByEmail(String email) {
        return peopleRepos.findByEmail(email);
    }
}
