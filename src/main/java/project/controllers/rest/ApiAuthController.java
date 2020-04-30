package project.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import project.dto.*;
import project.models.User;
import project.models.enums.ModerationStatus;
import project.services.AuthService;
import project.services.CaptchaCodeService;
import project.services.PostService;
import project.services.UserService;

import javax.validation.Valid;
import java.util.HashMap;

/**
 * Контроллер аутентификации
 */
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class ApiAuthController {
    private final UserService userService;
    private final PostService postService;
    private final CaptchaCodeService captchaCodeService;
    private final AuthService authService;

    /**
     * Вход пользователя
     */
    @PostMapping(value = "login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginUser(@RequestBody @Valid UnauthorizedUserDTO user) {
        User userFromDB = userService.getUserByEmailAndPassword(user.getEmail(), user.getPassword());
        return getUserResponseEntity(userFromDB);
    }

    /**
     * Проверка авторизации пользователя
     */
    @GetMapping("check")
    public ResponseEntity<?> checkUser() {
        Integer userId = authService.getUserId();
        User userFromDB = userService.getUserById(userId);
        return getUserResponseEntity(userFromDB);
    }

    /**
     * Восстановление пароля
     */
    @PostMapping(value = "restore",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> passwordRecovery(@RequestBody @Valid PasswordRecoveryDto passwordRecoveryDto) {
        boolean isValid = userService.isPasswordChanged(passwordRecoveryDto.getEmail());
        ResultDto result = new ResultDto();
        if (isValid) {
            result.setResult(true);
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("captcha")
    public ResponseEntity<CaptchaDto> getCaptcha() {
        return ResponseEntity.ok(captchaCodeService.getCaptchaDto());
    }

    @PostMapping(value = "register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDto register) {
        ResultDto result = new ResultDto();

        ErrorsMessageDto errorsMessage = errorsOnRegistration(register);
        if (errorsMessage != null) {
            return ResponseEntity.ok(errorsMessage);
        }
        userService.createUser(register.getEmail(), register.getPassword(), register.getName());
        result.setResult(true);
        return ResponseEntity.ok(result);
    }


    @PostMapping(value = "password",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto) {
        HashMap<String, String> errors = new HashMap<>();
        ErrorsMessageDto errorsMessageDto = new ErrorsMessageDto(errors);

        if (!captchaCodeService.isValid(changePasswordDto.getCaptcha(), changePasswordDto.getCaptchaSecret())) {
            errors.put("captcha", "Код с картинки введён неверно");
            return ResponseEntity.ok(errorsMessageDto);
        }

        ResultDto result = new ResultDto(true);
        userService.changePassword(changePasswordDto.getCode(), changePasswordDto.getPassword());
        return ResponseEntity.ok(result);
    }

    @GetMapping("logout")
    public ResponseEntity<?> logoutUser() {
        authService.checkSession();
        authService.logout();
        return ResponseEntity.ok(new ResultDto(true));
    }

    /**
     * Выдает в контроллер результат авторизации
     * (устраняет дублирование кода)
     */
    private ResponseEntity<?> getUserResponseEntity(User userFromDB) {
        if (userFromDB != null) {
            Integer countNewPosts = null;
            if (userFromDB.getModerator() == 1) {
                countNewPosts = postService.countPostsByActiveAndModerationStatus((byte) 1, ModerationStatus.NEW);
                return getAuthUserResponseEntity(userFromDB, true, true, countNewPosts);
            }
            return getAuthUserResponseEntity(userFromDB, false, false, countNewPosts);
        }
        return ResponseEntity.ok(new ResultDto());
    }

    /**
     * Собирает информацию об авторизованном пользователе для выдачи на front
     * (устраняет дублирование кода)
     */
    private ResponseEntity<AuthUserDto> getAuthUserResponseEntity(User userFromDB, boolean isModerator,
                                                                  boolean settings, Integer countNewPosts) {

        UserFullInformationDto userFullInformation = new UserFullInformationDto(userFromDB.getId(),
                userFromDB.getName(), userFromDB.getPhoto(), userFromDB.getEmail(),
                isModerator, countNewPosts, settings);
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        authService.saveSession(sessionId, userFromDB.getId());

        return ResponseEntity.ok(new AuthUserDto(userFullInformation));
    }

    private ErrorsMessageDto errorsOnRegistration(RegisterDto register) {
        HashMap<String, String> errors = new HashMap<>();
        ErrorsMessageDto errorsMessage = new ErrorsMessageDto(errors);
        boolean isUserPresent = userService.isUserByEmailPresent(register.getEmail());
        boolean isCaptchaValid = captchaCodeService.isValid(register.getCaptcha(), register.getCaptchaSecret());

        if (isUserPresent) {
            errors.put("email", "Этот e-mail уже зарегистрирован");
        }
        if (!isCaptchaValid) {
            errors.put("captcha", "Код с картинки введён неверно");
        }
        if (errors.size() != 0) {
            return errorsMessage;
        }
        return null;
    }
}
