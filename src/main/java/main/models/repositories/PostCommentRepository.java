package main.models.repositories;

import main.models.PostComment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentRepository extends CrudRepository<PostComment, Integer> {
    List<PostComment> findAllByPostId(Integer id);
}
