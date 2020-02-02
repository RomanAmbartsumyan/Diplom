package main.models.repositories;

import main.models.ModerationStatus;
import main.models.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий постов
 */
@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {
    List<Post> findDistinctByActiveAndModerationStatus(Byte active, ModerationStatus moderationStatus,
                                                       Pageable pageable);
}
