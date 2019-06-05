package com.gates.standstrong.domain.data.proximity;

import com.gates.standstrong.base.BaseResource;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/proximity-charts/{proximitySyncId}/limit/{limit}")
    public List<ProximityChart> getProximityCharts(@PathVariable Long proximitySyncId, @PathVariable Long limit){

        List<ProximityChart> result = proximityService.getProximityCharts(proximitySyncId, limit);

        return result;

    }
}
