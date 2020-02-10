package main.repositories;

import main.models.TagToPost;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий тегов к постам
 */
@Repository
public interface TagToPostRepository extends CrudRepository<TagToPost, Integer> {
    /**
     * Поиск все связи постов и тегов по полю post_id
     */
    List<TagToPost> findAllByPostId(Integer id);

    /**
     * Поиск все связи постов и тегов по полю tag_id
     */
    List<TagToPost> findAllByTagId(Integer id);
}
