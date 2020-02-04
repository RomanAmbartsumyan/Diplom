package main.repositories;

import main.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий пользователей
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
}
