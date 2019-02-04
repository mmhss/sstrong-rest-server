package com.gates.standstrong.domain.data.gps;

import com.gates.standstrong.base.BaseResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping(value = BaseResource.BASE_URL + GpsResource.RESOURCE_URL)
public class GpsResource extends BaseResource<Gps, GpsDto> {

    public static final String RESOURCE_URL = "/gpss";

    @Inject
    public GpsResource(GpsService gpsService, GpsMapper gpsMapper) {
        super(gpsService, gpsMapper, Gps.class, QGps.gps);
    }

}
