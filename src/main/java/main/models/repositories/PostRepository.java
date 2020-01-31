package main.models.repositories;

import main.models.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Репозиторий постов
 */
@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {
    @Query(value = "SELECT * FROM diplom.post WHERE is_active = 1 AND moderation_status = 1", nativeQuery = true)
    Set<Post> findAllBy(Pageable pageable);
}
