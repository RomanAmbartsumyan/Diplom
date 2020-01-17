package main.models.repositories;

import main.models.PostComment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCommentRepository extends CrudRepository<PostComment, Integer> {
}
