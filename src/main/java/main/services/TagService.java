package main.services;

import lombok.AllArgsConstructor;
import main.models.Tag;
import main.repositories.TagRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * Сервис для работы с тегами
 */
@Service
@AllArgsConstructor
public class TagService {
    /**
     * Репозиторий тегов
     */
    private TagRepository tagRepository;

    @PostConstruct
    public void init() {
        tagRepository.findByNameLike("asd");
        System.out.println();
    }

    /**
     * Выдает тег по Id
     */
    public Tag getTagById(Integer id) {
        Optional<Tag> optionalTag = tagRepository.findById(id);
        return optionalTag.orElse(null);
    }

    /**
     * Выдает тег по названию
     */
    public Tag getByName(String name) {
        Optional<Tag> optionalTag = tagRepository.findByNameLike(name);
        return optionalTag.orElse(null);
    }
}
