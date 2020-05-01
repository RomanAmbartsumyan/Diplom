package project.controllers.rest;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import project.services.AuthService;
import project.services.ImgService;

@RestController
@RequestMapping("/api/image")
@AllArgsConstructor
public class ApiImageController {
    private final ImgService imgService;
    private final AuthService authService;

    @SneakyThrows
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveImage(@RequestParam(value = "image", required = false) MultipartFile file) {
        authService.checkSession();
        String resultFileName = imgService.saveImg(file);
        return ResponseEntity.ok(resultFileName);
    }
}
