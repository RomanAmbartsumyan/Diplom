package main.repositories;

import main.models.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий тегов
 */
@Repository
public interface TagRepository extends CrudRepository<Tag, Integer> {
    /**
     * Поиск тэга по его id
     */
    Optional<Tag> findById(Integer id);
}
