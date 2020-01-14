package main.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер для обычных запросов
 */
@Controller
@RequestMapping("/")
public class DefaultController {

    public String index(){
        return "index";
    }
}
