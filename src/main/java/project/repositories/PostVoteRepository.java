package project.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.models.Post;
import project.models.PostVote;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий лайков и дизлайков
 */
@Repository
public interface PostVoteRepository extends CrudRepository<PostVote, Integer> {
    /**
     * Поиск информации по лайкам и дизлайкам к посту
     */
    List<PostVote> findAllByPostId(Post postId);

    @Query(value = "SELECT COUNT(*) FROM post_vote WHERE value = 1", nativeQuery = true)
    Integer countAllLikes();

    @Query(value = "SELECT COUNT(*) FROM post_vote WHERE value = -1", nativeQuery = true)
    Integer countAllDislikes();

    Optional<PostVote> findByPostIdAndUserIdAndValue(Post postId, Integer userId, Byte value);

    @Query(value = "SELECT COUNT(*) FROM post_vote WHERE value = :value AND user_id = :user_id", nativeQuery = true)
    Integer countAllByUserIdAndValue(@Param("user_id") Integer userId, @Param("value") Integer value);
}
