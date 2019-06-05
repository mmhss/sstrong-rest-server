package com.gates.standstrong.domain.data.proximity;

import com.gates.standstrong.base.BaseService;

import java.sql.Date;
import java.util.List;

public interface ProximityService extends BaseService<Proximity> {

    List<ProximityChart> getProximityChart(Long motherId);

    List<ProximityChart> getProximityCharts(Long proximityId, Long limit);

    List<SelfCare> getSelfCaredDays(Long motherId);

    List<ProximityChart> getProximityChartsByMother(Long motherId);

    List<Date> getDates(Long motherId);


}
