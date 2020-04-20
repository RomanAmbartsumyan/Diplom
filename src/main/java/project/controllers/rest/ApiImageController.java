package project.controllers.rest;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.dto.ImageDto;
import project.services.UserService;

import java.io.File;

@RestController
@RequestMapping("/api/image")
@AllArgsConstructor
public class ApiImageController {
    private UserService userService;

    @SneakyThrows
    @PostMapping
    private ResponseEntity<?> saveImage(@RequestBody ImageDto dto) {
        String actualPath = "src/main/resources/uploads/ab/cd/ef/";
        File uploadFolder = new File(actualPath);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdir();
        }
        String resultFileName = actualPath + dto.getImage().getOriginalFilename();
        dto.getImage().transferTo(new File(resultFileName));
        return ResponseEntity.ok(resultFileName);
    }
}
