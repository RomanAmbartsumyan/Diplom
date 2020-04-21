package project.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.dto.AddCommentDto;
import project.dto.CommentToPostDto;
import project.dto.ErrorsMessageDto;
import project.models.Post;
import project.models.PostComment;
import project.services.AuthService;
import project.services.PostCommentService;
import project.services.PostService;

import java.util.HashMap;

@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
public class ApiCommentController {
    private PostCommentService postCommentService;
    private PostService postService;
    private AuthService authService;

    @PostMapping
    private ResponseEntity<?> addCommentToPost(@RequestBody CommentToPostDto dto) {
        authService.checkSession();
        if (dto.getText().length() < 10) {
            HashMap<String, String> errors = new HashMap<>();
            ErrorsMessageDto errorsMessageDto = new ErrorsMessageDto(errors);
            errors.put("text", "Текст комментария не задан или слишком короткий");
            return ResponseEntity.ok(errorsMessageDto);
        }

        Integer userId = authService.getUserId();
        Post post = postService.getPostById(dto.getPostId());
        PostComment postComment = postCommentService.addComment(post, dto.getParentId(), userId, dto.getText());
        return ResponseEntity.ok(new AddCommentDto(postComment.getId()));
    }
}
