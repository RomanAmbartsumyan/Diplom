package project.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.models.TagToPost;
import project.repositories.TagToPostRepository;

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

    public void saveTagToPost(Integer postId, Integer tagId){
        TagToPost tagToPost = new TagToPost();
        tagToPost.setPostId(postId);
        tagToPost.setTagId(tagId);
        tagToPostRepository.save(tagToPost);
    }

    /**
     * Выдает колекцию связей постов и тегов по id поста
     */
    public List<TagToPost> getTagToPostByPostId(Integer id) {
        return tagToPostRepository.findAllByPostId(id);
    }

    /**
     * Выдает колекцию связей постов и тегов по id тэга
     */
    public List<TagToPost> getTagToPostByTagId(Integer id) {
        return tagToPostRepository.findAllByTagId(id);
    }

    /**
     * Выдает кол-во постов с коннкретным тэгом наденым по его id
     */
    public Integer countPostsWithTag(Integer tagId){
        return tagToPostRepository.countAllByTagId(tagId);
    }
}
