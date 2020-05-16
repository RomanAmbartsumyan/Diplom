package project.controllers.rest;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import project.dto.ErrorsMessageDto;
import project.services.AuthService;
import project.services.ImageService;

import java.util.HashMap;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ApiImageController {
    private final ImageService imageService;
    private final AuthService authService;

    @Value("${image.max.size}")
    private Long maxImageSize;

    @SneakyThrows
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveImage(@RequestParam(value = "image", required = false) MultipartFile file) {
        authService.checkSession();
        ErrorsMessageDto errorsMessageDto = errorMessage(file);
        if (errorsMessageDto != null) {
            return ResponseEntity.badRequest().body(errorsMessageDto);
        }
        String resultFileName = imageService.saveImg(file);
        return ResponseEntity.ok(resultFileName);
    }

    private ErrorsMessageDto errorMessage(MultipartFile file) {
        HashMap<String, String> errors = new HashMap<>();
        ErrorsMessageDto errorsMessageDto = new ErrorsMessageDto(errors);

        if (file != null) {
            boolean isPhotoValid = file.getSize() > maxImageSize;

            if (isPhotoValid) {
                errors.put("photo", "Фото слишком большое, нужно не более 5 Мб");
            }
            return errorsMessageDto;
        }
        return null;
    }
}
