package ru.mishin.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
// Добавление ко всем завпросам значения /first
// http://localhost:8080/hello –> http://localhost:8080/first/hello
//@RequestMapping("/first")
public class FirstController {
    @GetMapping("/hello")
    public String helloPage() {
        return "first/hello";
    }

    @GetMapping("/goodbye")
    public String goodByePage() {
        return "first/goodbye";
    }
}
