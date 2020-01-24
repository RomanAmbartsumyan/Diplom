package main.services;

import main.models.Post;
import main.models.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с БД постов
 */
@Service
public class PostService {
    /**
     * Репозиторий постов
     */
    private PostRepository postRepository;

    /**
     * Конструктор для репозитория
     */
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * Возвращает коллекцию всех постов
     */
    public List<Post> allPosts(){
        Iterable<Post> posts = postRepository.findAll();
        List<Post> postList = new ArrayList<>();
        posts.forEach(postList::add);
        return postList;
    }

    /**
     * Выдает пост по id
     */
    public ResponseEntity getPost (Integer id){
        Optional<Post> postById = postRepository.findById(id);

        return postById.map(post ->
                new ResponseEntity(post, HttpStatus.OK))
                .orElseGet(() ->
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }


}
