package main.services;

import lombok.AllArgsConstructor;
import main.models.TagToPost;
import main.repositories.TagToPostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с тегами и постами
 */
@Service
@AllArgsConstructor
public class TagToPostService {
    /**
     * Репозиторий связей постов и id
     */
    private TagToPostRepository tagToPostRepository;

    /**
     * Выдает колекцию связей постов и тегов
     */
    public List<TagToPost> getTagtoPostById(Integer id) {
        return tagToPostRepository.findAllByPostId(id);
    }
}
