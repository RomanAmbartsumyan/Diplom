package project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер для обычных запросов
 */
@Controller
@RequestMapping("/")
public class DefaultController {

    /**
     * Разворачивает фронт
     */
    public String index(){
        return "index";
    }
}
