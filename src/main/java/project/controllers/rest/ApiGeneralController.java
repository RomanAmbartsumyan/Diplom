package project.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.dto.responce.BlogGeneralInfiDto;

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
    public ResponseEntity<BlogGeneralInfiDto> mainPage() {
        return ResponseEntity.ok(new BlogGeneralInfiDto("DevPub", "Рассказы разботчиков"
                , "+7 903 666-44-55", "mail@mail.ru", "Дмитрий Сергеев", "2005"));
    }
}
