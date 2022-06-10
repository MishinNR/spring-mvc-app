package ru.mishin.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
@ComponentScan("ru.mishin.spring")
// Добавление поддержки в Spring WEB-функций (равносильно mvc:annotation-driven)
@EnableWebMvc
// Данный класс является аналогом applicationContextMVC.xml
// Имплиментируем интерфейс, для реализации метода configureViewResolvers().
// Этот интерфейс реализуется в том случае, когда хотим настроить под себя Spring MVC
// (в рассматриваем примере, хотим использовать вместо стандартного шаблонизатора, шаблонизатор Thymeleaf)
public class SpringConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;

    @Autowired
    // ApplicationContext будет внедрен автоматически Spring.
    // ApplicationContext используем в templateResolver() и templateEngine(),
    // где будет конфигурироваться представления.
    public SpringConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    // Настройка Thymeleaf
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        return templateResolver;
    } // (3)

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    } // (2)

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // Задаем тип шаблонизатора.
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        registry.viewResolver(resolver);
    } // (1)
}
