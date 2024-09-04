package ru.alishev.springcourse.FirstJWTApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.alishev.springcourse.FirstJWTApp.models.Person;
import ru.alishev.springcourse.FirstJWTApp.repositories.PeopleRepository;
import ru.alishev.springcourse.FirstJWTApp.security.PersonDetails;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final PeopleRepository personRepository;

    @Autowired
    public PersonDetailsService(PeopleRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByUsername(username);
        if(person.isPresent()) {
            return new PersonDetails(person.get());
        }
        else{
            throw new UsernameNotFoundException(username);
        }
    }
}
