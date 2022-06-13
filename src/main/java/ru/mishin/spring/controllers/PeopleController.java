package ru.mishin.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mishin.spring.dao.PersonDAO;
import ru.mishin.spring.models.Person;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()//http://localhost:8080/people
    public String index(Model model) {
        // Получим всех людей из DAO и предадим на отображение в представление
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}") //http://localhost:8080/people/{id}
    // Аннотация @PathVariable извлекает id из URL-адреса
    public String show(@PathVariable("id") int id, Model model) {
        // Получим одного человека по его id из DAO и предадим на отображение в представление
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    @GetMapping("/new")
    // Аннотация @ModelAttribute("person") сама создаст объект класса Model и положит его в модель
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") Person person) {
        // Если в форме ничего не будет записано в объект person,
        // то в модель будет записан объект со значениями по умолчанию
        personDAO.save(person);
        return "redirect:/people";
    }
}
