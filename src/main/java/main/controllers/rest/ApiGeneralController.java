package main.controllers.rest;

import main.dto.responce.BlogGeneralInfiDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер прочих запросов
 */
@RestController
@RequestMapping("/")
public class ApiGeneralController {

    @GetMapping("api/init")
    public BlogGeneralInfiDto getDefault(){
        return new BlogGeneralInfiDto("DevPub", "Рассказы разботчиков",
                "+7 903 666-44-55", "mail@mail.ru","Дмитрий Сергеев",
                "2005");
    }
}
