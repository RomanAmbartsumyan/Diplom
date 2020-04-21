package project.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.dto.ModerationDto;
import project.dto.ResultDto;
import project.services.AuthService;
import project.services.PostService;

@RestController
@RequestMapping("/api/moderation")
@AllArgsConstructor
public class ApiModerationController {
    private PostService postService;
    private AuthService authService;

    @PostMapping
    private ResponseEntity<?> postModeration(@RequestBody ModerationDto dto) {
        authService.checkSession();
        Integer moderatorId = authService.getUserId();
        postService.setModeration(dto.getPostId(), dto.getDecision(), moderatorId);
        return ResponseEntity.ok(new ResultDto(true));
    }
}
