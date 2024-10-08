package ru.alishev.springcourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alishev.springcourse.DAO.PersonDAO;
import ru.alishev.springcourse.Models.Person;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if(personDAO.getPersonByName(person.getName()).isPresent()){
            errors.rejectValue("name", "", "Name is already taken");
        }
    }
}
