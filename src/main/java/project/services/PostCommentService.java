package project.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.models.Post;
import project.models.PostComment;
import project.repositories.PostCommentRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис для работы с БД комментариев к постам
 */
@Service
@AllArgsConstructor
public class PostCommentService {
    /**
     * Репозиторий комментариев к постам
     */
    private final PostCommentRepository postCommentRepository;

    /**
     * Возвращает коллекцию всех комментариев к посту
     */
    public List<PostComment> allPostComments(Post post) {
        return postCommentRepository.findAllByPostId(post);
    }

    public PostComment addComment(Post post, Integer parentId, Integer userId, String text){
        PostComment postComment = new PostComment();
        postComment.setTime(LocalDateTime.now());
        postComment.setParenId(parentId);
        postComment.setPostId(post);
        postComment.setUserId(userId);
        postComment.setText(text);
        postCommentRepository.save(postComment);
        return postComment;
    }
}
