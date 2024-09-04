package ru.alishev.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alishev.springcourse.models.Person;

public interface PeopleRepos extends JpaRepository<Person, Integer> {
    Person findByEmail(String email);
}
