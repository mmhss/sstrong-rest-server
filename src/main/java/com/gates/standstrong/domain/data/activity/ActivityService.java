package com.gates.standstrong.domain.data.activity;

import com.gates.standstrong.base.BaseService;

import java.util.List;

public interface ActivityService extends BaseService<Activity> {
    List<Movement> getMovements(Long motherId);
}
