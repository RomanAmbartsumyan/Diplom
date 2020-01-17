package main.models.repositories;

import main.models.TagToPost;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagToPostRepository extends CrudRepository<TagToPost, Integer> {
}
