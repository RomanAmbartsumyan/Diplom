package project.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.dto.SettingsDto;
import project.models.GlobalSetting;
import project.models.enums.Decision;
import project.models.enums.Settings;
import project.repositories.GlobalSettingRepository;

import javax.annotation.PostConstruct;

@Service
@AllArgsConstructor
public class GlobalSettingService {
    private GlobalSettingRepository globalSettingRepository;

    @PostConstruct
    public void init() {
        GlobalSetting multiUserMode = new GlobalSetting();
        multiUserMode.setCode(Settings.MULTIUSER_MODE);
        multiUserMode.setName("Многопользовательский режим");
        multiUserMode.setValue(Decision.NO);

        GlobalSetting postPreModeration = new GlobalSetting();
        postPreModeration.setCode(Settings.POST_PREMODERATION);
        postPreModeration.setName("Премодерация постов");
        postPreModeration.setValue(Decision.YES);

        GlobalSetting statisticsIsPublic = new GlobalSetting();
        statisticsIsPublic.setCode(Settings.STATISTICS_IS_PUBLIC);
        statisticsIsPublic.setName("Показывать всем статистику блога");
        statisticsIsPublic.setValue(Decision.YES);


        globalSettingRepository.save(multiUserMode);
        globalSettingRepository.save(postPreModeration);
        globalSettingRepository.save(statisticsIsPublic);
    }

    public SettingsDto getSettings() {
        SettingsDto settingsDto = new SettingsDto();

        GlobalSetting multiUserMode = globalSettingRepository.findByCode(Settings.MULTIUSER_MODE);
        GlobalSetting postPreModeration = globalSettingRepository.findByCode(Settings.POST_PREMODERATION);
        GlobalSetting statisticsIsPublic = globalSettingRepository.findByCode(Settings.STATISTICS_IS_PUBLIC);

        if (multiUserMode.getValue().equals(Decision.YES)) {
            settingsDto.setMultiUserMode(true);
        } else {
            settingsDto.setMultiUserMode(false);
        }

        if (postPreModeration.getValue().equals(Decision.YES)) {
            settingsDto.setPostPreModeration(true);
        } else {
            settingsDto.setPostPreModeration(false);
        }

        if (statisticsIsPublic.getValue().equals(Decision.YES)) {
            settingsDto.setStatisticIsPublic(true);
        } else {
            settingsDto.setStatisticIsPublic(false);
        }
        return settingsDto;
    }

    public SettingsDto setSettings(SettingsDto dto) {
        SettingsDto settingsDto = new SettingsDto();

        GlobalSetting multiUserMode = globalSettingRepository.findByCode(Settings.MULTIUSER_MODE);
        GlobalSetting postPreModeration = globalSettingRepository.findByCode(Settings.POST_PREMODERATION);
        GlobalSetting statisticsIsPublic = globalSettingRepository.findByCode(Settings.STATISTICS_IS_PUBLIC);

        if (dto.isMultiUserMode()) {
            multiUserMode.setValue(Decision.YES);
            settingsDto.setMultiUserMode(true);
        } else {
            multiUserMode.setValue(Decision.NO);
            settingsDto.setMultiUserMode(false);
        }

        if (dto.isPostPreModeration()) {
            postPreModeration.setValue(Decision.YES);
            settingsDto.setPostPreModeration(true);
        } else {
            postPreModeration.setValue(Decision.NO);
            settingsDto.setPostPreModeration(false);
        }

        if (dto.isStatisticIsPublic()) {
            statisticsIsPublic.setValue(Decision.YES);
            settingsDto.setStatisticIsPublic(true);
        } else {
            statisticsIsPublic.setValue(Decision.NO);
            settingsDto.setStatisticIsPublic(false);
        }

        globalSettingRepository.save(multiUserMode);
        globalSettingRepository.save(postPreModeration);
        globalSettingRepository.save(statisticsIsPublic);

        return settingsDto;
    }

    public boolean isMultiUserModeOn() {
        GlobalSetting multiUserMode = globalSettingRepository.findByCode(Settings.MULTIUSER_MODE);
        return multiUserMode.getValue().equals(Decision.YES);
    }

    public boolean isPostPreModerationOn(){
        GlobalSetting multiUserMode = globalSettingRepository.findByCode(Settings.POST_PREMODERATION);
        return multiUserMode.getValue().equals(Decision.YES);
    }
}
