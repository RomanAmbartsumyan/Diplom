package main.repositories;

import main.models.ModerationStatus;
import main.models.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий постов
 */
@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {
    /**
     * Поиск всех постов по активности и статусу модерации
     */
    List<Post> findDistinctByActiveAndModerationStatus(Byte active, ModerationStatus moderationStatus,
                                                       Pageable pageable);

    /**
     * Поиск постов активности, статусу модерации, и наличие текста в заголовке
     */
    List<Post> findAllByActiveAndModerationStatusAndTitleContaining (Byte active, ModerationStatus moderationStatus,
                                                                   String title, Pageable pageable);

    /**
     * Поиск поста по id, активности, статусу модерации
     */
    Optional<Post> findByIdAndActiveAndModerationStatus(Integer id, Byte active, ModerationStatus moderationStatus);

    /**
     * Поиск постов по активности, статусу модерации и за конкретную дату
     */
    @Query(value = "SELECT * FROM post WHERE is_active like 1 and moderation_status like 1 " +
            "and time like :like_time", nativeQuery = true)
    List<Post> findAllByTimeContaining(@Param("like_time") String time, Pageable pageable);
}
