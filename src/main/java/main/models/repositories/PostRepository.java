package main.models.repositories;

import main.models.ModerationStatus;
import main.models.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
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
     * Поиск постов по id, активности и статусу модерации
     */
    Optional<Post> findByIdAndActiveAndModerationStatus(Integer id, Byte active, ModerationStatus moderationStatus);
}
