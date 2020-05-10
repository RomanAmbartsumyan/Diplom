package project.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.models.Post;
import project.models.enums.ModerationStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий постов
 */
@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {

    List<Post> findAllByActiveAndModerationStatusOrderByTimeDesc(Byte active, ModerationStatus moderationStatus,
                                                                 Pageable pageable);

    /**
     * Поиск постов по активности, статусу модерации, и наличие текста в заголовке
     */
    List<Post> findAllByActiveAndModerationStatusAndTitleContaining (Byte active, ModerationStatus moderationStatus,
                                                                   String title, Pageable pageable);

    /**
     * Поиск постов по активности, статусу модерации и за конкретную дату с ограничением вывода
     */
    @Query(value = "SELECT * FROM post WHERE is_active like 1 and moderation_status like 'ACCEPTED' " +
            "and time like :like_time", nativeQuery = true)
    List<Post> findAllByTimeContaining(@Param("like_time") String time, Pageable pageable);

    /**
     * Выдает значение кол-во постов
     */
    Integer countAllByActiveAndModerationStatus(Byte active, ModerationStatus moderationStatus);

    /**
     * Поиск постов по активности, статусу модерации и за конкретную дату без ограничения вывода
     */
    @Query(value = "SELECT * FROM post WHERE is_active like 1 and moderation_status like 'ACCEPTED' " +
            "and time like :like_time", nativeQuery = true)
    List<Post> findAllByTimeContaining(@Param("like_time") String time);

    /**
     * Выдает кол-во постов за конкретное время
     */
    @Query(value = "SELECT COUNT(*) FROM post WHERE time like :like_time", nativeQuery = true)
    Integer countAllByTimeContaining(@Param("like_time") String time);

    /**
     * Список годов в которых были посты
     */
    @Query(value = "SELECT DISTINCT YEAR (time) FROM post", nativeQuery = true)
    List<String> findAllYear();

    /**
     * Кол-во всех постов
     */
    @Query(value = "SELECT COUNT(*) FROM post", nativeQuery = true)
    Integer countAll();

    /**
     * Кол-во просмотров постов
     */
    @Query(value = "SELECT SUM(view_count) FROM post", nativeQuery = true)
    Integer countViews();

    @Query(value = "SELECT * FROM post ORDER BY time ASC limit 1", nativeQuery = true)
    Optional<Post> firstPublication();

    @Query(value = "SELECT post.* FROM post LEFT JOIN (SELECT * FROM post_vote " +
            "WHERE post_vote.value = 1) AS post_vote " +
            "ON post.id = post_vote.post_id " +
            "WHERE moderation_status = 'ACCEPTED'" +
            "AND is_active = 1 " +
            "AND post.time <= NOW() + INTERVAL 3 HOUR " +
            "GROUP BY post.id ORDER BY SUM(post_vote.value) DESC", nativeQuery = true)
    List<Post> bestPosts(Pageable pageable);

    @Query(value = "SELECT post.* FROM post LEFT JOIN post_comment " +
            "ON post.id = post_comment.post_id " +
            "WHERE moderation_status = 'ACCEPTED' " +
            "AND is_active = 1 " +
            "AND post.time <= NOW() + INTERVAL 3 HOUR " +
            "GROUP BY post.id " +
            "ORDER BY COUNT(post_comment.id) DESC", nativeQuery = true)
    List<Post> mostPopularPosts(Pageable pageable);

    List<Post> findAllByUserIdAndActiveOrderByTimeDesc(Integer userId, Byte active, Pageable pageable);

    List<Post> findAllByUserIdAndActiveAndModerationStatusOrderByTimeDesc(Integer userId, Byte active, ModerationStatus status,
                                                                          Pageable pageable);

    List<Post> findAllByModerationStatusAndActiveAndTimeBeforeOrderByTimeAsc(ModerationStatus moderationStatus,
                                                                       Byte active,
                                                                       LocalDateTime time,
                                                                       Pageable pageable);

    List<Post> findAllByModerationStatusAndActiveAndTimeBeforeOrderByTimeDesc(ModerationStatus moderationStatus,
                                                                              Byte active,
                                                                              LocalDateTime time,
                                                                              Pageable pageable);

    Integer countAllByUserId(Integer userId);

    @Query(value = "SELECT SUM(view_count) FROM post WHERE user_id = :user_id", nativeQuery = true)
    Integer countMyViews(@Param("user_id") Integer userId);

    @Query(value = "SELECT * FROM post WHERE user_id = :user_id ORDER BY time ASC limit 1", nativeQuery = true)
    Optional<Post> myFirstPublication(@Param("user_id") Integer userId);

    @Query(value = "SELECT DISTINCT post.* FROM post " +
            "LEFT JOIN tag2post " +
            "ON post.id = tag2post.post_id " +
            "LEFT JOIN tag " +
            "ON tag2post.tag_id = tag.id " +
            "WHERE is_active = 1 " +
            "AND name = :tag_name " +
            "AND time <= NOW() + INTERVAL 3 HOUR;", nativeQuery = true)
    List<Post> activePostsWithTagByName(@Param("tag_name") String tagName);
}