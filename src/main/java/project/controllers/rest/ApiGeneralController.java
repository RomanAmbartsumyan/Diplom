package project.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.dto.BlogGeneralInfoDto;

/**
 * Контроллер прочих запросов
 */
@RestController
@RequestMapping("/")
@AllArgsConstructor
public class ApiGeneralController {
    /**
     * Запись в футер сайта
     */
    @GetMapping("api/init")
    public ResponseEntity<BlogGeneralInfoDto> mainPage() {
        return ResponseEntity.ok(new BlogGeneralInfoDto("DevPub", "Рассказы разботчиков",
                "+7 985 433-43-07", "r9854334307@mail.ru", "Роман Амбарцумян", "2020"));
    }
}
