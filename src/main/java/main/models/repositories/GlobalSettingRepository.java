package main.models.repositories;

import main.models.GlobalSetting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalSettingRepository extends CrudRepository<GlobalSetting, Integer> {
}
