package project.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import project.dto.*;
import project.models.User;
import project.services.AuthService;
import project.services.CaptchaCodeService;
import project.services.PostService;
import project.services.UserService;

import javax.validation.Valid;

/**
 * Контроллер аутентификации
 */
@RestController
@RequestMapping("/api/auth/")
@AllArgsConstructor
public class ApiAuthController {
    private UserService userService;
    private PostService postService;
    private CaptchaCodeService captchaCodeService;
    private AuthService authService;


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
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        Integer userId = authService.getUserId(sessionId);
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
        if(captchaCodeService.isValid(register.getCaptcha(), register.getCaptchaSecret())){
            userService.createUser(register.getEmail(), register.getName(), register.getPassword());
            result.setResult(true);
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "password",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto){
        ResultDto result = new ResultDto();
        if(captchaCodeService.isValid(changePasswordDto.getCaptcha(), changePasswordDto.getCaptchaSecret())){
            userService.changePassword(changePasswordDto.getCode(), changePasswordDto.getPassword());
            result.setResult(true);
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * Выдает в контроллер результат авторизации
     * (устраняет дублирование кода)
     */
    private ResponseEntity<?> getUserResponseEntity(User userFromDB) {
        ResultDto result = new ResultDto();
        if (userFromDB != null) {
            Integer countNewPosts = null;
            if (userFromDB.getModerator() != null) {
                countNewPosts = postService.getCountOfNewPosts();
                return getAuthUserResponseEntity(userFromDB, true, true, countNewPosts);
            }
            return getAuthUserResponseEntity(userFromDB, false, false, countNewPosts);
        }
        return ResponseEntity.ok(result);
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
}
