package com.gates.standstrong.domain.data.gps;

import com.gates.standstrong.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class GpsServiceImpl extends BaseServiceImpl<Gps> implements GpsService {

    private GpsRepository gpsRepository;

    @Inject
    public GpsServiceImpl(GpsRepository gpsRepository) {
        super(gpsRepository);
    }
}
