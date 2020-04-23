package project.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dto.SettingsDto;
import project.services.AuthService;
import project.services.GlobalSettingService;

@RestController
@RequestMapping("/api/settings")
@AllArgsConstructor
public class ApiSettingsController {
    private final GlobalSettingService globalSettingService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<SettingsDto> getSettings(){
        SettingsDto settings = globalSettingService.getSettings();
        return ResponseEntity.ok(settings);
    }

    @PutMapping
    public ResponseEntity<SettingsDto> setSettings(@RequestBody SettingsDto dto){
        authService.checkSession();
        SettingsDto settings = globalSettingService.setSettings(dto);
        return ResponseEntity.ok(settings);
    }
}
