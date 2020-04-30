package project.controllers.rest;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.dto.ImageDto;
import project.services.ImgService;
import project.services.UserService;

@RestController
@RequestMapping("/api/image")
@AllArgsConstructor
public class ApiImageController {
    private final UserService userService;
    private final ImgService imgService;

    @SneakyThrows
    @PostMapping
    private ResponseEntity<?> saveImage(@RequestBody ImageDto dto) {
        String resultFileName = imgService.saveImg(dto.getImage());
        return ResponseEntity.ok(resultFileName);
    }
}
