package project.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.exceptions.BadRequestException;
import project.exceptions.NotFountException;
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
        return optionalTag.orElseThrow(NotFountException::new);
    }

    /**
     * Выдает тег по названию
     */
    public Tag getByName(String name) {
        Optional<Tag> optionalTag = tagRepository.findByNameLike(name);
        return optionalTag.orElseThrow(BadRequestException::new);
    }

    /**
     * Если запрос пустой выдает все теги, если нет, то конкретные содержащие данное название
     */
    public List<Tag> getAllTagsOrFindByName(String query){
        if (query == null || query.isEmpty()){
            return tagRepository.findAll();
        }
        return  tagRepository.findAllByNameContains(query);
    }
}
