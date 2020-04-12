package project.services;

import org.springframework.stereotype.Service;
import project.models.Post;
import project.models.PostComment;
import project.repositories.PostCommentRepository;

import java.util.List;

/**
 * Сервис для работы с БД комментариев к постам
 */
@Service
public class PostCommentService {
    /**
     * Репозиторий комментариев к постам
     */
    private PostCommentRepository postCommentRepository;

    /**
     * Конструктор для репозитория
     */

    public PostCommentService(PostCommentRepository postCommentRepository) {
        this.postCommentRepository = postCommentRepository;
    }

    /**
     * Возвращает коллекцию всех комментариев к посту
     */
    public List<PostComment> allPostComments(Post post) {
        return postCommentRepository.findAllByPostId(post);
    }
}
