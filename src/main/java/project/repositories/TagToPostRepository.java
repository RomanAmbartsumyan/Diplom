package project.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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

    @Query(value = "SELECT COUNT(*) FROM tag2post " +
            "LEFT JOIN post " +
            "ON post.id = post_id " +
            "WHERE post.time <= NOW() + INTERVAL 3 HOUR " +
            "AND tag_id = :tag_id " +
            "GROUP BY tag_id", nativeQuery = true)
    Integer countAllByTagId(@Param("tag_id") Integer tagId);

    Optional<TagToPost> findByPostIdAndTagId(Integer postId, Integer tagId);

    @Transactional
    void deleteAllByPostId(Integer postId);

    @Query(value = "SELECT COUNT(DISTINCT post_id) FROM tag2post " +
            "LEFT JOIN post " +
            "ON post.id = tag2post.post_id " +
            "WHERE moderation_status = 'ACCEPTED' " +
            "AND is_active = 1 " +
            "AND post.time <= NOW() + INTERVAL 3 HOUR", nativeQuery = true)
    Integer countPostsWithTags();
}
