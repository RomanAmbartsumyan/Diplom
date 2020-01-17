package main.models.repositories;

import main.models.CaptchaCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaptchaCodeRepository extends CrudRepository<CaptchaCode, Integer> {
}
