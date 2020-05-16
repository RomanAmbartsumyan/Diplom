package project.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/profile/my")
@RequiredArgsConstructor
public class ApiProfileController {
    private final UserService userService;
    private final AuthService authService;
    private final ImageService imageService;

    @Value("${image.max.size}")
    private Long maxImageSize;

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
            deletePhoto(user);
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
            deletePhoto(user);
        }

        userService.editUserProfileWithoutPhoto(user, dto);
        return ResponseEntity.ok(new ResultDto(true));
    }


    private void deletePhoto(User user) {
        Pattern path = Pattern.compile("src/([\\s\\S]*?).(jpg|png)");
        String photo = user.getPhoto();
        Matcher matcher = path.matcher(photo);
        String actualPath = null;

        while (matcher.find()) {
            actualPath = matcher.group();
        }

        imageService.removePhoto(actualPath);
    }

    private ErrorsMessageDto errorMessageWithoutPhoto(ProfileDto dto, User user) {
        HashMap<String, String> errors = new HashMap<>();
        ErrorsMessageDto errorsMessageDto = new ErrorsMessageDto(errors);
        boolean isEmailPresent = userService.isUserByEmailPresent(dto.getEmail());

        return getErrorsMessageDto(dto, user, errors, errorsMessageDto, isEmailPresent);
    }


    private ErrorsMessageDto errorMessageWithPhoto(ProfileDto dto, User user, MultipartFile file) {
        HashMap<String, String> errors = new HashMap<>();
        ErrorsMessageDto errorsMessageDto = new ErrorsMessageDto(errors);
        boolean isEmailPresent = userService.isUserByEmailPresent(dto.getEmail());

        if (file != null) {
            boolean isPhotoValid = file.getSize() > maxImageSize;

            if (isPhotoValid) {
                errors.put("photo", "Фото слишком большое, нужно не более 5 Мб");
            }
        }

        return getErrorsMessageDto(dto, user, errors, errorsMessageDto, isEmailPresent);
    }

    private ErrorsMessageDto getErrorsMessageDto(ProfileDto dto, User user, HashMap<String, String> errors,
                                                 ErrorsMessageDto errorsMessageDto, boolean isEmailPresent) {
        if (dto.getName() != null) {
            boolean validName = dto.getName().replaceAll("[а-яА-ЯёЁa-zA-Z0-9]", "").isEmpty();
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
