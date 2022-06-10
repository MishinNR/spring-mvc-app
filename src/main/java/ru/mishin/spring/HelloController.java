package ru.mishin.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("/hello-world")
    public String sayHello() {
        // Возрвщаем пользователю view из /WEB-INF/views/hello_world.html
        return "hello_world";
    }
}
