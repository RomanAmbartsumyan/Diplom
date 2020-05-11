package project.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.models.Tag;
import project.repositories.TagRepository;

import java.util.List;
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
    private final TagRepository tagRepository;

    public Tag saveTag(String tagName){
        Tag tag = new Tag();
        tag.setName(tagName);
        tagRepository.save(tag);
        return tag;
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

    public List<Tag> tagsOnActivePosts() {
        return tagRepository.tagsOnActivePosts();
    }
}
