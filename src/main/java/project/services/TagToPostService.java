package project.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.models.TagToPost;
import project.repositories.TagToPostRepository;

import javax.annotation.PostConstruct;
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

    @PostConstruct
    public void init() {
        TagToPost tagToPost = new TagToPost();
        tagToPost.setTagId(345);
        tagToPostRepository.save(tagToPost);
        tagToPostRepository.countAllByTagId(345);
        System.out.println();
    }

    /**
     * Выдает колекцию связей постов и тегов по id поста
     */
    public List<TagToPost> getTagtoPostByPostId(Integer id) {
        return tagToPostRepository.findAllByPostId(id);
    }

    /**
     * Выдает колекцию связей постов и тегов по id тэга
     */
    public List<TagToPost> getTagtoPostByTagId(Integer id) {
        return tagToPostRepository.findAllByTagId(id);
    }

    /**
     * Выдает кол-во постов с коннкретным тэгом наденым по его id
     */
    public Integer countPostsWithTag(Integer tagId){
        return tagToPostRepository.countAllByTagId(tagId);
    }
}
