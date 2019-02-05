package com.gates.standstrong.domain.data.activity;


import com.gates.standstrong.base.BaseResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping(value = BaseResource.BASE_URL + ActivityResource.RESOURCE_URL)
public class ActivityResource extends BaseResource<Activity, ActivityDto> {

    public static final String RESOURCE_URL = "/activities";

    @Inject
    public ActivityResource(ActivityService activityService, ActivityMapper activityMapper) {
        super(activityService, activityMapper, Activity.class, QActivity.activity);
    }
}