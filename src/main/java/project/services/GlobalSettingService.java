package project.services;

import org.springframework.stereotype.Service;
import project.repositories.GlobalSettingRepository;

@Service
public class GlobalSettingService {
    private GlobalSettingRepository globalSettingRepository;

    public GlobalSettingService(GlobalSettingRepository globalSettingRepository) {
        this.globalSettingRepository = globalSettingRepository;
    }
}
