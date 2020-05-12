package project.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.dto.ErrorsMessageDto;
import project.dto.ProfileDto;
import project.dto.ResultDto;
import project.models.User;
import project.services.AuthService;
import project.services.ImageService;
import project.services.UserService;

import java.util.HashMap;

@RestController
@RequestMapping("/api/profile/my")
@AllArgsConstructor
public class ApiProfileController {
    private final UserService userService;
    private final AuthService authService;
    private final ImageService imageService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> editProfileWithPhoto(@RequestParam(value = "photo", required = false) MultipartFile file,
                                                  @ModelAttribute ProfileDto dto) {
        authService.checkSession();
        Integer userId = authService.getUserId();
        User user = userService.getUserById(userId);
        ErrorsMessageDto errorsMessageDto = errorMessageWithPhoto(dto, user, file);
        if (errorsMessageDto != null) {
            return ResponseEntity.badRequest().body(errorsMessageDto);
        }

        if (user.getPhoto() != null) {
            imageService.removePhoto(user.getPhoto().substring(22));
        }

        String image = imageService.saveImg(file);
        userService.editUserProfileWithPhoto(user, dto, image);
        return ResponseEntity.ok(new ResultDto(true));
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> editProfile(@RequestBody ProfileDto dto) {
        authService.checkSession();
        Integer userId = authService.getUserId();
        User user = userService.getUserById(userId);
        ErrorsMessageDto errorsMessageDto = errorMessageWithoutPhoto(dto, user);
        if (errorsMessageDto != null) {
            return ResponseEntity.badRequest().body(errorsMessageDto);
        }
        if (dto.getRemovePhoto() == 1) {
            imageService.removePhoto(user.getPhoto().substring(22));
        }

        userService.editUserProfileWithoutPhoto(user, dto);
        return ResponseEntity.ok(new ResultDto(true));
    }

    private ErrorsMessageDto errorMessageWithoutPhoto(ProfileDto dto, User user) {
        HashMap<String, String> errors = new HashMap<>();
        ErrorsMessageDto errorsMessageDto = new ErrorsMessageDto(errors);
        boolean isEmailPresent = userService.isUserByEmailPresent(dto.getEmail());

        if (dto.getName() != null) {
            boolean validName = dto.getName().replaceAll("\\w", "").isEmpty();
            if (!validName) {
                errors.put("name", "Имя указанно неверно");
            }
        }

        if (isEmailPresent && !user.getEmail().equals(dto.getEmail())) {
            errors.put("email", "Этот e-mail уже зарегистрирован");
        }

        if (!errors.isEmpty()) {
            return errorsMessageDto;
        }
        return null;
    }


    private ErrorsMessageDto errorMessageWithPhoto(ProfileDto dto, User user, MultipartFile file) {
        HashMap<String, String> errors = new HashMap<>();
        ErrorsMessageDto errorsMessageDto = new ErrorsMessageDto(errors);
        boolean isEmailPresent = userService.isUserByEmailPresent(dto.getEmail());

        if (file != null) {
            boolean isPhotoValid = file.getSize() > 5_242_880;

            if (isPhotoValid) {
                errors.put("photo", "Фото слишком большое, нужно не более 5 Мб");
            }
        }

        if (dto.getName() != null) {
            boolean validName = dto.getName().replaceAll("\\w", "").isEmpty();
            if (!validName) {
                errors.put("name", "Имя указанно неверно");
            }
        }

        if (isEmailPresent && !user.getEmail().equals(dto.getEmail())) {
            errors.put("email", "Этот e-mail уже зарегистрирован");
        }

        if (!errors.isEmpty()) {
            return errorsMessageDto;
        }
        return null;
    }
}
