package project.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import project.dto.responce.AuthUser;
import project.dto.responce.PasswordRecoveryDto;
import project.dto.responce.UnauthorizedUserDTO;
import project.dto.responce.UserFullInformation;
import project.models.User;
import project.services.PostService;
import project.services.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Контроллер аутентификации
 */
@RestController
@RequestMapping("/api/auth/")
@AllArgsConstructor
public class ApiAuthController {
    private UserService userService;
    private PostService postService;
    private Map<String, Integer> authUsers;

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
        Integer userId = authUsers.get(sessionId);
        User userFromDB = userService.getUserById(userId);
        return getUserResponseEntity(userFromDB);
    }

    /**
     * Восстановление пароля
     */
    @PostMapping(value = "restore",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> passwordRecovery(@RequestBody @Valid PasswordRecoveryDto passwordRecoveryDto){
        HashMap<String, String> result = new HashMap<>(1);
        boolean isValid = userService.isPasswordChanged(passwordRecoveryDto.getEmail());
        if(isValid){
            result.put("result", "true");
            return ResponseEntity.ok(result);
        }
        result.put("result", "false");
        return ResponseEntity.ok(result);
    }


    /**
     * Выдает в контроллер результат авторизации
     * (устраняет дублирование кода)
     */
    private ResponseEntity<?> getUserResponseEntity(User userFromDB) {
        Map<String, String> fallsAuth = new HashMap<>(1);
        if (userFromDB != null) {
            Integer countNewPosts = null;
            if (userFromDB.getModerator() == 1) {
                countNewPosts = postService.getCountOfNewPosts();
                return getAuthUserResponseEntity(userFromDB, true, true, countNewPosts);
            }
            return getAuthUserResponseEntity(userFromDB, false, false, countNewPosts);
        } else {
            fallsAuth.put("result", "false");
        }
        return ResponseEntity.ok(fallsAuth);
    }
    /**
     * Собирает информацию об авторизованном пользователе для выдачи на front
     * (устраняет дублирование кода)
     */
    private ResponseEntity<AuthUser> getAuthUserResponseEntity(User userFromDB, boolean isModerator,
                                                               boolean settings, Integer countNewPosts) {

        UserFullInformation userFullInformation = new UserFullInformation(userFromDB.getId(),
                userFromDB.getName(), userFromDB.getPhoto(), userFromDB.getEmail(),
                isModerator, countNewPosts, settings);
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        authUsers.put(sessionId, userFromDB.getId());

        return ResponseEntity.ok(new AuthUser(userFullInformation));
    }
}
