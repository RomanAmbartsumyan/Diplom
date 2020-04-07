package project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.models.Post;
import project.models.PostComment;

import java.util.List;

/**
 * Репозиторий комментариев к постам
 */
@Repository
public interface PostCommentRepository extends CrudRepository<PostComment, Integer> {
    /**
     * Поиск коментариев к постам по полю post_id
     */
    List<PostComment> findAllByPostId(Post postId);
}
