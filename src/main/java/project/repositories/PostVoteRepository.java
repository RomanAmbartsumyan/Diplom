package project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.models.PostVote;

import java.util.List;

/**
 * Репозиторий лайков и дизлайков
 */
@Repository
public interface PostVoteRepository extends CrudRepository<PostVote, Integer> {
    /**
     * Поиск информации по лайкам и дизлайкам к посту
     */
    List<PostVote> findAllByPostId(Integer id);
}
