package project.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.dto.ModerationDto;
import project.dto.ResultDto;
import project.services.PostService;

@RestController
@RequestMapping("/api/moderation")
@AllArgsConstructor
public class ApiModerationController {
    private PostService postService;

    @PostMapping
    private ResponseEntity<?> postModeration(@RequestBody ModerationDto dto) {
        postService.setModeration(dto.getPostId(), dto.getDecision());
        return ResponseEntity.ok(new ResultDto(true));
    }
}
