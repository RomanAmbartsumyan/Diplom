package project.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.models.Tag;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий тегов
 */
@Repository
public interface TagRepository extends CrudRepository<Tag, Integer> {
    /**
     * Поиск тэга по его id
     */
    Optional<Tag> findById(Integer id);

    /**
     * Поиск тега по названию
     */
    Optional<Tag> findByNameLike(String name);

    /**
     * Выдает все тэги по наименованию
     */
    List<Tag> findAllByNameContains(String name);

    /**
     * Выдает все тэги по наименованию
     */
    List<Tag> findAll();

    @Query(value = "SELECT DISTINCT tag.* FROM tag " +
            "LEFT JOIN tag2post " +
            "ON tag.id = tag2post.tag_id " +
            "LEFT JOIN post " +
            "ON tag2post.post_id = post.id " +
            "WHERE is_active = 1 " +
            "AND moderation_status = 'ACCEPTED' " +
            "AND post.time <= NOW() + INTERVAL 3 HOUR", nativeQuery = true)
    List<Tag> tagsOnActivePosts();
}
