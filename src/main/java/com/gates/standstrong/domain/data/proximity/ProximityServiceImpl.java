package com.gates.standstrong.domain.data.proximity;

import com.gates.standstrong.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Date;
import java.util.List;

@Service
public class ProximityServiceImpl extends BaseServiceImpl<Proximity> implements ProximityService {

    private ProximityRepository proximityRepository;

    @Inject
    public ProximityServiceImpl(ProximityRepository proximityRepository) {

        super(proximityRepository);
        this.proximityRepository = proximityRepository;

    }


    @Override
    public List<ProximityChart> getProximityChart(Long motherId) {
        return proximityRepository.getProximityChart(motherId);
    }

    @Override
    public List<ProximityChart> getProximityCharts(Long proximitySyncId) {
        return proximityRepository.getProximityCharts(proximitySyncId);
    }

    @Override
    public List<SelfCare> getSelfCaredDays(Long motherId) {
        return proximityRepository.getSelfCaredDays(motherId);
    }

    @Override
    public List<ProximityChart> getProximityChartsByMother(Long motherId) {
        return proximityRepository.getProximityChartsByMother(motherId);
    }

    @Override
    public List<Date> getDates(Long motherId) {
        return proximityRepository.getDates(motherId);
    }


}
