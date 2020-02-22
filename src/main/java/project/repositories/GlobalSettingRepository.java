package project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.models.GlobalSetting;

/**
 * Репозиторий глобальных настроек
 */
@Repository
public interface GlobalSettingRepository extends CrudRepository<GlobalSetting, Integer> {
}
