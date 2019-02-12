package com.gates.standstrong.domain.data.activity;

import com.gates.standstrong.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class ActivityServiceImpl extends BaseServiceImpl<Activity> implements ActivityService{

    private ActivityRepository activityRepository;

    @Inject
    public ActivityServiceImpl(ActivityRepository activityRepository) {
        super(activityRepository);
        this.activityRepository = activityRepository;
    }

    @Override
    public List<Movement> getMovements(Long motherId) {
        return activityRepository.getMovements(motherId);
    }
}
