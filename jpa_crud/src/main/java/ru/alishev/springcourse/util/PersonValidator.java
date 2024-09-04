package ru.alishev.springcourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alishev.springcourse.models.Person;
import ru.alishev.springcourse.repositories.PeopleRepos;

@Component
public class PersonValidator implements Validator {

    private final PeopleRepos peopleRepos;

    @Autowired
    public PersonValidator(PeopleRepos peopleRepos) {
        this.peopleRepos = peopleRepos;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        //check unique email
        if(peopleRepos.findByEmail(person.getEmail())!=null){
            errors.rejectValue("email", "", "Email already exists");
        }
    }
}
