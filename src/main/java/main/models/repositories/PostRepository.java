package main.models.repositories;

import main.models.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * Репозиторий постов
 */
@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {
}
