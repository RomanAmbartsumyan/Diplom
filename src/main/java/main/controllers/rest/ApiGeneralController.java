package main.controllers.rest;

import main.dto.responce.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Контроллер прочих запросов
 */
@RestController
@RequestMapping("/")
public class ApiGeneralController {

    @GetMapping("api/init")
    ResponseEntity<BlogGeneralInfiDto> mainPage() {
        return ResponseEntity.ok(new BlogGeneralInfiDto("DevPub", "Рассказы разботчиков"
                , "+7 903 666-44-55", "mail@mail.ru", "Дмитрий Сергеев", "2005"));
    }

    @GetMapping("api/post")
    ResponseEntity<PostList> postList(@RequestParam(defaultValue = "10") Integer limit,
                                      @RequestParam(defaultValue = "0") Integer offset,
                                      @RequestParam(defaultValue = "recent") String mode) {
        LocalDateTime localDateTime = LocalDateTime.now();
        User user = new User(88, "Дмитрий Петров");
        Set<Posts> postsSet = new HashSet<>();
        postsSet.add(new Posts(345, localDateTime, user,
                "Заголовок поста", "Текст анонса поста без HTML-тэгов",
                36, 3, 15, 55));
        return ResponseEntity.ok(new PostList(390, postsSet, limit, offset, mode));
    }
}
