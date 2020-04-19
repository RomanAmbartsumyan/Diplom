package project.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.models.Post;
import project.models.PostComment;
import project.repositories.PostCommentRepository;

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
    private PostCommentRepository postCommentRepository;

    /**
     * Возвращает коллекцию всех комментариев к посту
     */
    public List<PostComment> allPostComments(Post post) {
        return postCommentRepository.findAllByPostId(post);
    }
}
