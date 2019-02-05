package com.gates.standstrong.domain.data.proximity;

import com.gates.standstrong.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
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
}
