package project.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.models.ModerationStatus;
import project.models.Post;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий постов
 */
@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {
    /**
     * Поиск всех постов по активности и статусу модерации
     */
    List<Post> findDistinctByActiveAndModerationStatus(Byte active, ModerationStatus moderationStatus,
                                                       Pageable pageable);

    /**
     * Поиск постов по активности, статусу модерации, и наличие текста в заголовке
     */
    List<Post> findAllByActiveAndModerationStatusAndTitleContaining (Byte active, ModerationStatus moderationStatus,
                                                                   String title, Pageable pageable);

    /**
     * Собирает коллекцию постов по id, активности и статусу модерации
     */
    @Query(value = "SELECT * FROM post WHERE id IN :ids AND is_active LIKE 1 " +
            "AND moderation_status LIKE 1 ", nativeQuery = true)
    List<Post> findByIdIn(@Param("ids")List<Integer> ids);

    /**
     * Поиск постов по id, активности, статусу модерации
     */
    Optional<Post> findByIdAndActiveAndModerationStatus(Integer id, Byte active, ModerationStatus moderationStatus);

    /**
     * Поиск постов по активности, статусу модерации и за конкретную дату с ограничением вывода
     */
    @Query(value = "SELECT * FROM post WHERE is_active like 1 and moderation_status like 1 " +
            "and time like :like_time", nativeQuery = true)
    List<Post> findAllByTimeContaining(@Param("like_time") String time, Pageable pageable);

    /**
     * Выдает значение кол-во постов
     */
    @Query(value = "SELECT COUNT(*) FROM post WHERE is_active like 1 and moderation_status", nativeQuery = true)
    Integer countByActiveAndModerationStatus();

    /**
     * Поиск постов по активности, статусу модерации и за конкретную дату без ограничения вывода
     */
    @Query(value = "SELECT * FROM post WHERE is_active like 1 and moderation_status like 1 " +
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
     * Выдает кол-во новых постов
     */
    @Query(value = "SELECT COUNT(*) FROM post WHERE moderation_status = 0", nativeQuery = true)
    Integer countALLByModerationStatusIsNew();

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

    @Query(value = "SELECT * FROM post ORDER BY time DESC limit 1", nativeQuery = true)
    Optional<Post> firstPublication();
}