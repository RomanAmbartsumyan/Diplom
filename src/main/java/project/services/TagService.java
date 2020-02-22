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
    private TagRepository tagRepository;

//    @PostConstruct
//    public void init() {
//        Tag tag = new Tag();
//        tag.setName("sajd");
//        tagRepository.save(tag);
//    }

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

    /**
     * Если запрос пустой выдает все теги, если нет, то конкретные содержащие данное название
     */
    public List<Tag> getAllTagsOrFindByName(String query){
        if (query.isEmpty()){
            return tagRepository.findAll();
        }
        return  tagRepository.findAllByNameContains(query);
    }

}
