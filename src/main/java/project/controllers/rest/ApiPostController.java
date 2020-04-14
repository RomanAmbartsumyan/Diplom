package project.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dto.*;
import project.exceptions.UnauthorizedException;
import project.models.*;
import project.services.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Контроллер постов
 */
@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
public class ApiPostController {
    private PostService postService;
    private UserService userService;
    private PostCommentService postCommentService;
    private PostVoteService postVoteService;
    private TagService tagService;
    private TagToPostService tagToPostService;
    private AuthService authService;


    @PostMapping
    public ResponseEntity<?> addPost(@RequestBody AddPostDto addPost) {
        if (authService.checkSession()) {
            ResponseEntity<?> errorsMessage = errorOperationWithPost(addPost);
            if (errorsMessage != null){
                return errorsMessage;
            }
            Integer userId = authService.getUserId();
            Post post = postService.createPost(userId, addPost);

            addTags(addPost, post);
            return ResponseEntity.ok(new ResultDto(true));
        }
        throw new UnauthorizedException();
    }


    /**
     * Вывод всех постов на главную страницу
     */
    @GetMapping
    public ResponseEntity<PostListDto> postList(@RequestParam Integer offset,
                                                @RequestParam Integer limit,
                                                @RequestParam String mode) {

        List<Post> posts = postService.findAllAndSort(offset, limit, mode);
        List<PostDto> allPosts = transformCollectionForFront(posts);
        Integer quantityPosts = postService.countPostsByActiveAndModerationStatus((byte) 1, ModerationStatus.ACCEPTED);

        return ResponseEntity.ok(new PostListDto(quantityPosts, allPosts, offset, limit, mode));
    }

    /**
     * Выдает посты удовлетворяющие поиску
     */
    @GetMapping("search")
    public ResponseEntity<PostSearchDto> postsBySearch(@RequestParam Integer offset,
                                                       @RequestParam Integer limit,
                                                       @RequestParam(required = false) String query) {
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
        List<PostVote> postVotes = postVoteService.getAllPostVotesByPostId(postById);

        byte quantityLike = (byte) postVotes.stream().filter(postVote -> postVote.getValue() == 1).count();
        byte quantityDislike = (byte) (postVotes.size() - quantityLike);

        List<PostComment> postComments = postCommentService.allPostComments(postById);

        List<CommentsDto> comments = postComments.stream().map(postComment -> {
            UserWithPhotoInformationDto user = userService.getFullInformationById(postComment.getUserId());
            return new CommentsDto(postComment.getId(), postComment.getTime(), postComment.getText(), user);
        }).collect(toList());


        List<TagToPost> tagToPosts = tagToPostService.getTagToPostByPostId(postById.getId());

        List<String> tagNames = tagToPosts.stream().map(tagToPost -> {
            Tag tag = tagService.getTagById(tagToPost.getTagId());
            return tag.getName();
        }).collect(toList());

        Integer countComments = postComments.size();

        return ResponseEntity.ok(new PostByIdDto(postId, time, userDto, title, text,
                quantityLike, quantityDislike, countComments, viewCount, comments, tagNames));
    }


    @PutMapping("{id}")
    public ResponseEntity<?> editPost(@RequestBody AddPostDto addPost, @PathVariable Integer id) {
        if (authService.checkSession()) {
            Integer userId = authService.getUserId();
            User user = userService.getUserById(userId);
            Post post = postService.editingPost(id, user, addPost);
            ResponseEntity<?> errorsMessage = errorOperationWithPost(addPost);
            if (errorsMessage != null){
                return errorsMessage;
            }

            addTags(addPost, post);
            return ResponseEntity.ok(new ResultDto(true));
        }
        throw new UnauthorizedException();
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
                                                         @RequestParam(required = false) String tag) {


        Tag tagByName = tagService.getByName(tag);
        List<TagToPost> tagToPosts = tagToPostService.getTagToPostByTagId(tagByName.getId());
        List<Integer> postsId = tagToPosts.stream().map(TagToPost::getPostId).collect(toList());

        List<Post> posts = postsId.stream().map(post -> postService.getPostById(post)).collect(toList());
        List<PostDto> allPosts = transformCollectionForFront(posts);
        Integer quantityPosts = allPosts.size();

        return ResponseEntity.ok(new PostListDto(quantityPosts, allPosts, offset, limit, tag));
    }

    @GetMapping("moderation")
    private ResponseEntity<ModerationPostsDto> getPostsListOnModeration(@RequestParam Integer offset,
                                                                        @RequestParam Integer limit,
                                                                        @RequestParam String status) {
        if (authService.checkSession()) {
            Integer moderatorId = authService.getUserId();
            List<Post> posts = postService.activePostsOnModeration(offset, limit, status, moderatorId);

            List<PostsOnModerationDto> postsOnModeration = posts.stream().map(post -> {
                User user = userService.getUserById(post.getUserId());
                UserDto userDto = new UserDto(user.getId(), user.getName());
                return new PostsOnModerationDto(post.getId(), post.getTime(), userDto, post.getTitle(), post.getText());
            }).collect(toList());

            Integer countPosts = postsOnModeration.size();

            return ResponseEntity.ok(new ModerationPostsDto(countPosts, postsOnModeration));
        }
        throw new UnauthorizedException();
    }

    @GetMapping("my")
    public ResponseEntity<?> getMyPosts(@RequestParam Integer offset,
                                        @RequestParam Integer limit,
                                        @RequestParam String status) {
        if (authService.checkSession()) {
            Integer userId = authService.getUserId();
            List<Post> posts = postService.getMyPosts(userId, offset, limit, status);
            List<PostDto> myPosts = transformCollectionForFront(posts);
            Integer quantityPosts = myPosts.size();

            return ResponseEntity.ok(new MyPostListDto(quantityPosts, myPosts, offset, limit, status));
        }
        throw new UnauthorizedException();
    }

    @PostMapping("like")
    private ResponseEntity<?> addLike(@RequestBody PostVoteDto postVote){
        if (authService.checkSession()) {
            Post post = postService.getPostById(postVote.getPostId());
            Integer userId = authService.getUserId();
            boolean isLikeAdded = postVoteService.addLike(post, userId);
            return ResponseEntity.ok(new ResultDto(isLikeAdded));
        }
        throw new UnauthorizedException();
    }

    @PostMapping("dislike")
    private ResponseEntity<?> addDislike(@RequestBody PostVoteDto postVote){
        if (authService.checkSession()) {
            Post post = postService.getPostById(postVote.getPostId());
            Integer userId = authService.getUserId();
            boolean isDislikeAdded = postVoteService.addDislike(post, userId);
            return ResponseEntity.ok(new ResultDto(isDislikeAdded));
        }
        throw new UnauthorizedException();
    }


    /**
     * Метод устраняет дублирование
     */
    private List<PostDto> transformCollectionForFront(List<Post> posts) {
        return posts.stream().map(post -> {
            UserDto userDto = userService.getUserDtoById(post.getUserId());
            List<PostVote> postVotes = postVoteService.getAllPostVotesByPostId(post);

            int quantityComment = postCommentService.allPostComments(post).size();
            byte quantityLike = (byte) postVotes.stream().filter(postVote -> postVote.getValue() == 1).count();
            byte quantityDislike = (byte) (postVotes.size() - quantityLike);

            return new PostDto(post.getId(), post.getTime(), userDto,
                    post.getTitle(), post.getText(), quantityLike,
                    quantityDislike, quantityComment, post.getViewCount());
        }).collect(toList());
    }

    private ResponseEntity<?> errorOperationWithPost(@RequestBody AddPostDto addPost) {
        boolean tittle = addPost.getTitle().isEmpty() || addPost.getTitle().length() < 10 ||
                addPost.getTitle().length() > 500;
        boolean text = addPost.getText().isEmpty() || addPost.getText().length() < 10 ||
                addPost.getText().length() > 500;

        if (tittle || text) {
            ErrorsMessageDto errorsMessage = new ErrorsMessageDto();
            ErrorsDto error = new ErrorsDto();

            if (tittle) {
                error.setTitle("Заголовок не установлен");
                errorsMessage.setErrors(error);
            }

            if (text) {
                error.setText("Текст публикации слишком короткий");
                errorsMessage.setErrors(error);
            }
            return ResponseEntity.ok(errorsMessage);
        }
        return null;
    }

    private void addTags(@RequestBody AddPostDto addPost, Post post) {
        if (addPost.getTags().length != 0) {
            for (int i = 0; i < addPost.getTags().length; i++) {
                Tag tag = tagService.saveTag(addPost.getTags()[i]);
                tagToPostService.saveTagToPost(post.getId(), tag.getId());
            }
        }
    }
}
