package ru.mishin.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mishin.spring.dao.PersonDAO;
import ru.mishin.spring.models.Person;
import ru.mishin.spring.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()// http://localhost:8080/people
    public String index(Model model) {
        // Получим всех людей из DAO и предадим на отображение в представление
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}") // http://localhost:8080/people/{id}
    // Аннотация @PathVariable извлекает id из URL-адреса
    public String show(@PathVariable("id") int id, Model model) {
        // Получим одного человека по его id из DAO и предадим на отображение в представление
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    @GetMapping("/new") // http://localhost:8080/people/new
    // Аннотация @ModelAttribute("person") сама создаст объект класса Model и положит его в модель
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()// http://localhost:8080/people <– POST
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {

        personValidator.validate(person, bindingResult); // Генерация ошибки через собственный валидатор

        if (bindingResult.hasErrors())
            return "people/new";
        // Если в форме ничего не будет записано в объект person,
        // то в модель будет записан объект со значениями по умолчанию
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit") // http://localhost:8080/people/{id}/edit
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}") // http://localhost:8080/people/{id} <– PATCH
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {

        personValidator.validate(person, bindingResult); // Генерация ошибки через собственный валидатор

        if (bindingResult.hasErrors())
            return "people/edit";
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}") // http://localhost:8080/people/{id} <– DELETE
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}
