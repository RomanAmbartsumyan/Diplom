package project.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dto.*;
import project.models.*;
import project.models.enums.ModerationStatus;
import project.services.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Контроллер постов
 */
@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
public class ApiPostController {
    private final PostService postService;
    private final UserService userService;
    private final PostCommentService postCommentService;
    private final PostVoteService postVoteService;
    private final TagService tagService;
    private final TagToPostService tagToPostService;
    private final AuthService authService;
    private final GlobalSettingService globalSettingService;


    @PostMapping
    public ResponseEntity<?> addPost(@RequestBody AddPostDto addPost) {
        authService.checkSession();
        ResponseEntity<ErrorsMessageDto> errorsMessage = errorOperationWithPost(addPost);
        if (errorsMessage != null) {
            return errorsMessage;
        }
        Integer userId = authService.getUserId();
        User user = userService.getUserById(userId);
        if(user.getModerator() == 1 || globalSettingService.isMultiUserModeOn()){
            Post post = postService.createPost(user, addPost);

            addTags(addPost.getTags(), post.getId());
            return ResponseEntity.ok(new ResultDto(true));
        }
        return null;
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
            user.setPhoto("");
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
        authService.checkSession();
        Integer userId = authService.getUserId();
        User user = userService.getUserById(userId);
        Post post = postService.editingPost(id, user, addPost);
        ResponseEntity<ErrorsMessageDto> errorsMessage = errorOperationWithPost(addPost);
        if (errorsMessage != null) {
            return errorsMessage;
        }

        addTags(addPost.getTags(), post.getId());
        return ResponseEntity.ok(new ResultDto(true));
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
    public ResponseEntity<ModerationPostsDto> getPostsListOnModeration(@RequestParam Integer offset,
                                                                        @RequestParam Integer limit,
                                                                        @RequestParam String status) {
        authService.checkSession();
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

    @GetMapping("my")
    public ResponseEntity<?> getMyPosts(@RequestParam Integer offset,
                                        @RequestParam Integer limit,
                                        @RequestParam String status) {
        authService.checkSession();
        Integer userId = authService.getUserId();
        List<Post> posts = postService.getMyPosts(userId, offset, limit, status);
        List<PostDto> myPosts = transformCollectionForFront(posts);
        Integer quantityPosts = myPosts.size();

        return ResponseEntity.ok(new MyPostListDto(quantityPosts, myPosts, offset, limit, status));
    }

    @PostMapping("like")
    public ResponseEntity<ResultDto> addLike(@RequestBody PostVoteDto postVote) {
        authService.checkSession();
        Post post = postService.getPostById(postVote.getPostId());
        Integer userId = authService.getUserId();
        boolean isLikeAdded = postVoteService.addLike(post, userId);
        return ResponseEntity.ok(new ResultDto(isLikeAdded));
    }

    @PostMapping("dislike")
    public ResponseEntity<ResultDto> addDislike(@RequestBody PostVoteDto postVote) {
        authService.checkSession();
        Post post = postService.getPostById(postVote.getPostId());
        Integer userId = authService.getUserId();
        boolean isDislikeAdded = postVoteService.addDislike(post, userId);
        return ResponseEntity.ok(new ResultDto(isDislikeAdded));
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

    private ResponseEntity<ErrorsMessageDto> errorOperationWithPost(@RequestBody AddPostDto addPost) {
        boolean tittle = addPost.getTitle().isEmpty() || addPost.getTitle().length() < 10 ||
                addPost.getTitle().length() > 500;
        boolean text = addPost.getText().isEmpty() || addPost.getText().length() < 10 ||
                addPost.getText().length() > 500;

        if (tittle || text) {
            HashMap<String, String> errors = new HashMap<>();
            ErrorsMessageDto errorsMessage = new ErrorsMessageDto(errors);

            if (tittle) {
                errors.put("title", "Заголовок не установлен");
            }

            if (text) {
                errors.put("text", "Текст публикации слишком короткий");
            }
            return ResponseEntity.ok(errorsMessage);
        }
        return null;
    }

    private void addTags(String[] tags, Integer postId) {
        if (tags.length != 0) {
            for (String s : tags) {
                Tag tagFromDb = tagService.getByName(s);
                if (tagFromDb != null) {
                    if (!tagToPostService.isTagToPostPresent(postId, tagFromDb.getId())) {
                        tagToPostService.saveTagToPost(postId, tagFromDb.getId());
                    }
                    continue;
                }
                Tag tag = tagService.saveTag(s);
                tagToPostService.saveTagToPost(postId, tag.getId());
            }
        }
    }
}
