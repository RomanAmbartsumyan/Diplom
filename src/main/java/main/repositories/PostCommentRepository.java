package main.repositories;

import main.models.PostComment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий комментариев к постам
 */
@Repository
public interface PostCommentRepository extends CrudRepository<PostComment, Integer> {
    /**
     * Поиск коментариев к постам по полю post_id
     */
    List<PostComment> findAllByPostId(Integer id);
}
