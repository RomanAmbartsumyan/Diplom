package project.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dto.SettingsDto;
import project.models.User;
import project.services.AuthService;
import project.services.GlobalSettingService;
import project.services.UserService;

@RestController
@RequestMapping("/api/settings")
@AllArgsConstructor
public class ApiSettingsController {
    private final GlobalSettingService globalSettingService;
    private final AuthService authService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<SettingsDto> getSettings(){
        SettingsDto settings = globalSettingService.getSettings();
        return ResponseEntity.ok(settings);
    }

    @PutMapping
    public ResponseEntity<SettingsDto> setSettings(@RequestBody SettingsDto dto){
        authService.checkSession();
        Integer userId = authService.getUserId();
        User user = userService.getUserById(userId);
        SettingsDto settings = globalSettingService.setSettings(dto, user);
        return ResponseEntity.ok(settings);
    }
}
