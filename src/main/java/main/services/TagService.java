package main.services;

import lombok.AllArgsConstructor;
import main.models.Tag;
import main.models.repositories.TagRepository;
import org.springframework.stereotype.Service;

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

    /**
     * Выдает тег по Id
     */
    public Tag getTagById(Integer id) {
        Optional<Tag> optionalTag = tagRepository.findById(id);
        return optionalTag.orElse(null);
    }
}
