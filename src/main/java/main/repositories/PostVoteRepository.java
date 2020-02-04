package main.repositories;

import main.models.PostVote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий лайков и дизлайков
 */
@Repository
public interface PostVoteRepository extends CrudRepository<PostVote, Integer> {
    List<PostVote> findAllByPostId(Integer id);
}
