package ru.mishin.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
// Добавление ко всем завпросам значения /first
// http://localhost:8080/hello –> http://localhost:8080/first/hello
@RequestMapping("/first")
public class FirstController {
    @GetMapping("/hello")
    // Внедрение класса HttpServletRequest, для обработки параметра GET запроса
    public String helloPage(HttpServletRequest request) {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        System.out.println("Hello, " + name + " " + surname);

        return "first/hello";
    }

    @GetMapping("/goodbye")
    public String goodByePage() {
        return "first/goodbye";
    }
}
