package project.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.dto.ErrorsMessageDto;
import project.dto.ProfileDto;
import project.dto.ResultDto;
import project.models.User;
import project.services.AuthService;
import project.services.UserService;

import java.util.HashMap;

@RestController
@RequestMapping("/api/profile/my")
@AllArgsConstructor
public class ApiProfileController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<?> editProfile(@RequestBody ProfileDto dto) {
        authService.checkSession();
        Integer userId = authService.getUserId();
        User user = userService.getUserById(userId);
        ErrorsMessageDto errorsMessageDto = errorMessage(dto, user);
        if (errorsMessageDto != null) {
            return ResponseEntity.ok(errorsMessageDto);
        }

        userService.editUserProfile(user, dto);
        return ResponseEntity.ok(new ResultDto(true));
    }

    private ErrorsMessageDto errorMessage(ProfileDto dto, User user) {
        HashMap<String, String> errors = new HashMap<>();
        ErrorsMessageDto errorsMessageDto = new ErrorsMessageDto(errors);
        boolean isEmailPresent = userService.isUserByEmailPresent(dto.getEmail());
//        boolean isPhotoValid = dto.getPhoto().getSize() > 5_242_880;

        if (isEmailPresent && !user.getEmail().equals(dto.getEmail())) {
            errors.put("email", "Этот e-mail уже зарегистрирован");
        }


//            if (isPhotoValid) {
//                errors.put("photo", "Фото слишком большое, нужно не более 5 Мб");
//            }

        if (errors.size() != 0) {
            return errorsMessageDto;
        }
        return null;
    }
}
