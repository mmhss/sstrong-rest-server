package com.gates.standstrong.domain.data.activity;

import com.gates.standstrong.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ActivityServiceImpl extends BaseServiceImpl<Activity> implements ActivityService{

    private ActivityRepository activityRepository;

    @Inject
    public ActivityServiceImpl(ActivityRepository activityRepository) {
        super(activityRepository);
    }
}
