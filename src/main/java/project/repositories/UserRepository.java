package project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.models.User;

/**
 * Репозиторий пользователей
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
}
