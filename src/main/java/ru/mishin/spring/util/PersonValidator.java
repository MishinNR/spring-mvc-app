package ru.mishin.spring.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.mishin.spring.dao.PersonDAO;
import ru.mishin.spring.models.Person;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        // Ограничиваем валидатор только на классе Person
        return Person.class.equals(clazz);
    }

    @Override
    // Этот метод будет вызываться на контроллере на объекте класса Person,
    // который приходит с формы.
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        // Нужно посмотреть, есть ли человек с таким же email в БД
        if (personDAO.show(person.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "This email is already taken!");
        }
    }
}
