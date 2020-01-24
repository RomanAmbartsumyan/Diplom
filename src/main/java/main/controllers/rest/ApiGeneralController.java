package main.controllers.rest;

import main.dto.responce.*;
import main.models.Post;
import main.models.PostVote;
import main.models.User;
import main.services.PostCommentService;
import main.services.PostService;
import main.services.PostVoteService;
import main.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Контроллер прочих запросов
 */
@RestController
@RequestMapping("/")
public class ApiGeneralController {
    private PostService postService;
    private UserService userService;
    private PostCommentService postCommentService;
    private PostVoteService postVoteService;

    /**
     * Конструктор для контроллера
     */
    public ApiGeneralController(PostService postService, UserService userService,
                                PostCommentService postCommentService, PostVoteService postVoteService) {
        this.postService = postService;
        this.userService = userService;
        this.postCommentService = postCommentService;
        this.postVoteService = postVoteService;
    }

    /**
     * Запись в футер сайта
     */
    @GetMapping("api/init")
    ResponseEntity<BlogGeneralInfiDto> mainPage() {
        return ResponseEntity.ok(new BlogGeneralInfiDto("DevPub", "Рассказы разботчиков"
                , "+7 903 666-44-55", "mail@mail.ru", "Дмитрий Сергеев", "2005"));
    }

    /**
     * Вывод всех постов на главную страницу
     */
    @GetMapping("api/post")
    ResponseEntity<PostList> postList(@RequestParam Integer offset,
                                      @RequestParam Integer limit,
                                      @RequestParam String mode) {
        List<Posts> postsList = new ArrayList<>();
        List<Post> posts = postService.allPosts();

        posts.forEach(post -> {
            User user = userService.getUserById(post.getUserId());
            UserDto userDto = new UserDto(post.getUserId(), user.getName());
            PostVote postVote = postVoteService.getPostVoteById(post.getUserId());

            int count = postCommentService.allPostComments(post.getId()).size();

            postsList.add(new Posts(post.getId(), post.getTime(), userDto,
                    post.getTitle(), post.getText(),
                    postVote.getLike(), postVote.getDislike(), count, post.getViewCount()));
        });

        Integer postCount = postsList.size();
        return ResponseEntity.ok(new PostList(postCount, postsList, offset, limit, mode));
    }
}
