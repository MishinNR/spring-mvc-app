package ru.mishin.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
// Добавление ко всем завпросам значения /first
// http://localhost:8080/hello –> http://localhost:8080/first/hello
@RequestMapping("/first")
public class FirstController {
    @GetMapping("/hello")
    // Внедрение аннотации RequestParam, для обработки параметра GET запроса
    public String helloPage(@RequestParam("name") String name,
                            @RequestParam("surname") String surname) {
        System.out.println("Hello, " + name + " " + surname);

        return "first/hello";
    }

    @GetMapping("/goodbye")
    public String goodByePage() {
        return "first/goodbye";
    }
}
