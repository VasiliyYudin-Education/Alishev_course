package ru.alishev.springcourse.AlishevProject2Boot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alishev.springcourse.AlishevProject2Boot.DAO.PersonDAO;
import ru.alishev.springcourse.AlishevProject2Boot.Models.Person;

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
