package main.services;

import main.models.repositories.GlobalSettingRepository;
import org.springframework.stereotype.Service;

@Service
public class GlobalSettingService {
    private GlobalSettingRepository globalSettingRepository;

    public GlobalSettingService(GlobalSettingRepository globalSettingRepository) {
        this.globalSettingRepository = globalSettingRepository;
    }
}
