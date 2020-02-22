package project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.models.CaptchaCode;

/**
 * Репозиторий капчи
 */
@Repository
public interface CaptchaCodeRepository extends CrudRepository<CaptchaCode, Integer> {
}
