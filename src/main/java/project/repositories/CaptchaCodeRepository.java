package project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.models.CaptchaCode;

import java.util.Optional;

/**
 * Репозиторий капчи
 */
@Repository
public interface CaptchaCodeRepository extends CrudRepository<CaptchaCode, Integer> {
    Optional<CaptchaCode> findByCodeAndSecretCode(String code, String secretCode);
}
