package project.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dto.*;
import project.models.*;
import project.services.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Контроллер постов
 */
@RestController
@RequestMapping("/api/post/")
@AllArgsConstructor
public class ApiPostController {
    private PostService postService;
    private UserService userService;
    private PostCommentService postCommentService;
    private PostVoteService postVoteService;
    private TagService tagService;
    private TagToPostService tagToPostService;
    private AuthService authService;


    /**
     * Вывод всех постов на главную страницу
     */
    @GetMapping
    public ResponseEntity<PostListDto> postList(@RequestParam Integer offset,
                                                @RequestParam Integer limit,
                                                @RequestParam String mode) {

        List<Post> posts = postService.findAllAndSort(offset, limit, mode);
        List<PostDto> allPosts = transformCollectionForFront(posts);
        Integer quantityPosts = allPosts.size();

        return ResponseEntity.ok(new PostListDto(quantityPosts, allPosts, offset, limit, mode));
    }

    /**
     * Выдает посты удовлетворяющие поиску
     */
    @GetMapping("search")
    public ResponseEntity<PostSearchDto> postsBySearch(@RequestParam Integer offset,
                                                       @RequestParam Integer limit,
                                                       @RequestParam String query) {
        List<Post> findingPost = postService.findBySearch(offset, limit, query);
        List<PostDto> allFindingPost = transformCollectionForFront(findingPost);
        Integer quantityPosts = allFindingPost.size();

        return ResponseEntity.ok(new PostSearchDto(quantityPosts, allFindingPost, offset, limit, query));
    }

    /**
     * Выдает пост по id
     */
    @GetMapping("{id}")
    public ResponseEntity<PostByIdDto> getPostById(@PathVariable Integer id) {
        Post postById = postService.getPostById(id);
        UserDto userDto = userService.getUserDtoById(postById.getUserId());
        Integer postId = postById.getId();
        LocalDateTime time = postById.getTime();
        String title = postById.getTitle();
        String text = postById.getText();
        Integer viewCount = postById.getViewCount();
        List<PostVote> postVotes = postVoteService.getAllPostVotesByPostId(postById.getId());

        byte quantityLike = (byte) postVotes.stream().filter(postVote -> postVote.getValue() == 1).count();
        byte quantityDislike = (byte) (postVotes.size() - quantityLike);

        List<PostComment> postComments = postCommentService.allPostComments(id);
        List<CommentsDto> comments = new ArrayList<>();

        postComments.forEach(postComment -> {
            UserWithPhotoInformationDto user = userService.getFullInformationById(postComment.getUserId());
            comments.add(new CommentsDto(postComment.getId(), postComment.getTime(), user, postComment.getText()));
        });

        List<String> tagNames = new ArrayList<>();
        List<TagToPost> tagToPosts = tagToPostService.getTagToPostByPostId(postById.getId());
        tagToPosts.forEach(tagToPost -> {
            Tag tag = tagService.getTagById(tagToPost.getTagId());
            tagNames.add(tag.getName());
        });

        return ResponseEntity.ok(new PostByIdDto(postId, time, userDto, title, text,
                quantityLike, quantityDislike, viewCount, comments, tagNames));
    }


    /**
     * Выдает посты за конкретную дату
     */
    @GetMapping("byDate")
    public ResponseEntity<PostListDto> getPostsByDate(@RequestParam Integer offset,
                                                      @RequestParam Integer limit,
                                                      @RequestParam String date) {

        List<Post> posts = postService.findPostsByDate(offset, limit, date);
        List<PostDto> allPosts = transformCollectionForFront(posts);
        Integer quantityPosts = allPosts.size();

        return ResponseEntity.ok(new PostListDto(quantityPosts, allPosts, offset, limit, date));
    }

    /**
     * Выдает посты по тегу
     */
    @GetMapping("byTag")
    public ResponseEntity<PostListDto> getPostsByTagName(@RequestParam Integer offset,
                                                         @RequestParam Integer limit,
                                                         @RequestParam String tagName) {
        List<Integer> postsId = new ArrayList<>();

        Tag tag = tagService.getByName(tagName);
        List<TagToPost> tagToPosts = tagToPostService.getTagToPostByTagId(tag.getId());
        tagToPosts.forEach(tagToPost -> postsId.add(tagToPost.getPostId()));

        List<Post> posts = postService.getAllPostsById(postsId);
        List<PostDto> allPosts = transformCollectionForFront(posts);
        Integer quantityPosts = allPosts.size();

        return ResponseEntity.ok(new PostListDto(quantityPosts, allPosts, offset, limit, tagName));
    }

    @GetMapping("moderation")
    private ResponseEntity<ModerationPostsDto> getPostListOnModeration(Integer offset, Integer limit, String status){
        if(authService.checkSession()){
            Integer countPosts = postService.countActivePosts();
            List<Post> posts = postService.activePostsOnModeration(offset, limit, status);

            List<PostsOnModerationDto> postsOnModeration =  posts.stream().map(post -> {
                User user = userService.getUserById(post.getUserId());
                UserDto userDto = new UserDto(user.getId(), user.getName());
                return new PostsOnModerationDto(post.getId(), post.getTime(), userDto, post.getTitle(), post.getText());
            }).collect(toList());

            return ResponseEntity.ok(new ModerationPostsDto(countPosts, postsOnModeration));
        }
        return null;
    }

    /**
     * Метод устраняет дублирование
     */
    private List<PostDto> transformCollectionForFront(List<Post> posts) {
        List<PostDto> allPosts = new ArrayList<>();
        posts.forEach(post -> {
            UserDto userDto = userService.getUserDtoById(post.getUserId());
            List<PostVote> postVotes = postVoteService.getAllPostVotesByPostId(post.getId());

            int quantityComment = postCommentService.allPostComments(post.getId()).size();
            byte quantityLike = (byte) postVotes.stream().filter(postVote -> postVote.getValue() == 1).count();
            byte quantityDislike = (byte) (postVotes.size() - quantityLike);

            allPosts.add(new PostDto(post.getId(), post.getTime(), userDto,
                    post.getTitle(), post.getText(), quantityLike,
                    quantityDislike, quantityComment, post.getViewCount()));
        });
        return allPosts;
    }
}
