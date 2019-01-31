package com.gates.standstrong.domain.data.proximity;

import com.gates.standstrong.base.BaseService;

import java.util.List;

public interface ProximityService extends BaseService<Proximity> {

    List<ProximityChart> getProximityChart(String identificationNumber);

}
