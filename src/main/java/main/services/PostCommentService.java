package main.services;

import main.models.Post;
import main.models.PostComment;
import main.models.repositories.PostCommentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для работы с БД комментариев к постам
 */
@Service
public class PostCommentService {
    /**
     * Репозиторий комментариев к постам
     */
    PostCommentRepository postCommentRepository;
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
        Iterable<PostComment> posts = postCommentRepository.findAllById(id);
        List<PostComment> postCommentList = new ArrayList<>();
        posts.forEach(postCommentList::add);
        return postCommentList;
    }


}
