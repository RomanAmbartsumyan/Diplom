package main.services;

import main.models.PostComment;
import main.models.repositories.PostCommentRepository;
import org.springframework.stereotype.Service;

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
    public List<PostComment> allPostComments(Integer id){
        return postCommentRepository.findAllByPostId(id);
    }


}
