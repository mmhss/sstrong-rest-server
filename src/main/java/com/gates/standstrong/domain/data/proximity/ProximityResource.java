package com.gates.standstrong.domain.data.proximity;

import com.gates.standstrong.base.BaseResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = BaseResource.BASE_URL + ProximityResource.RESOURCE_URL)
public class ProximityResource extends BaseResource<Proximity, ProximityDto> {

    public static final String RESOURCE_URL = "/proximities";

    private ProximityService proximityService;

    @Inject
    public ProximityResource(ProximityService proximityService, ProximityMapper proximityMapper) {
        super(proximityService, proximityMapper, Proximity.class, QProximity.proximity);
        this.proximityService = proximityService;
    }

    @GetMapping({"/proximitychart"})
    public List<ProximityChart> getProximityChart(){

        List<ProximityChart> result = proximityService.getProximityChart("SS1001");

        return result;

    }
}
