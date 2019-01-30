package com.gates.standstrong.domain.setting;

import com.gates.standstrong.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SettingServiceImpl extends BaseServiceImpl<Setting> implements SettingService {

    @Inject
    public SettingServiceImpl(SettingRepository settingRepository){
        super(settingRepository);
    }
}
