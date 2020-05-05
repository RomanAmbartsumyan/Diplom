package project.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.models.TagToPost;

import java.util.List;
import java.util.Optional;

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

    Integer countAllByTagId(Integer tagId);

    Optional<TagToPost> findByPostIdAndTagId(Integer postId, Integer tagId);

    @Query(value = "SELECT DISTINCT tag2post.tag_id " +
            "FROM tag2post " +
            "LEFT JOIN post " +
            "ON post.id = tag2post.post_id " +
            "WHERE is_active = 1 " +
            "AND moderation_status = 'ACCEPTED' " +
            "AND time <= NOW() + INTERVAL 3 HOUR", nativeQuery = true)
    List<Integer> getTagIds();

}
