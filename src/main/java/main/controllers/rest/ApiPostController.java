package main.controllers.rest;

import lombok.AllArgsConstructor;
import main.dto.responce.PostList;
import main.dto.responce.PostSearch;
import main.dto.responce.Posts;
import main.dto.responce.UserDto;
import main.models.Post;
import main.models.PostVote;
import main.services.PostCommentService;
import main.services.PostService;
import main.services.PostVoteService;
import main.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер постов
 */
@RestController
@RequestMapping("/api/post/")
@AllArgsConstructor
public class ApiPostController {
    private final PostService postService;
    private final UserService userService;
    private final PostCommentService postCommentService;
    private final PostVoteService postVoteService;


    /**
     * Вывод всех постов на главную страницу
     */
    @GetMapping
    public ResponseEntity<PostList> postList(@RequestParam Integer offset,
                                             @RequestParam Integer limit,
                                             @RequestParam String mode) {

        List<Post> posts = postService.findAll(offset, limit, mode);
        List<Posts> allPosts = tranformCollectionForFront(posts);
        Integer quantityPosts = allPosts.size();

        return ResponseEntity.ok(new PostList(quantityPosts, allPosts, offset, limit, mode));
    }

    @GetMapping("search")
    public ResponseEntity<PostSearch> postSearch(@RequestParam Integer offset,
                                                 @RequestParam Integer limit,
                                                 @RequestParam String query) {
        List<Post> findingPost = postService.findBySearch(offset, limit, query);
        List<Posts> allFindingPost= tranformCollectionForFront(findingPost);
        Integer quantityPosts = allFindingPost.size();

        return ResponseEntity.ok(new PostSearch(quantityPosts, allFindingPost, offset, limit, query));
    }

    public List<Posts> tranformCollectionForFront(List<Post> posts) {
        List<Posts> allPosts = new ArrayList<>();
        posts.forEach(post -> {
            UserDto userDto = userService.getUserById(post.getUserId());
            List<PostVote> postVotes = postVoteService.getAllPostVotesByPostId(post.getId());

            int quantityComment = postCommentService.allPostComments(post.getId()).size();
            byte quantityLike = (byte) postVotes.stream().filter(postVote -> postVote.getValue() == 1).count();
            byte quantityDislike = (byte) (postVotes.size() - quantityLike);

            allPosts.add(new Posts(post.getId(), post.getTime(), userDto,
                    post.getTitle(), post.getText(), quantityLike,
                    quantityDislike, quantityComment, post.getViewCount()));
        });
        return allPosts;
    }
}
