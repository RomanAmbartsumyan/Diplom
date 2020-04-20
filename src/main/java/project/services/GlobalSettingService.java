package project.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.repositories.GlobalSettingRepository;

@Service
@AllArgsConstructor
public class GlobalSettingService {
    private GlobalSettingRepository globalSettingRepository;

}
