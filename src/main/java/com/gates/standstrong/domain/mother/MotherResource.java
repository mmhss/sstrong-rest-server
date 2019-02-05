package com.gates.standstrong.domain.mother;

import com.gates.standstrong.base.BaseResource;
import com.gates.standstrong.domain.data.proximity.ProximityChart;
import com.gates.standstrong.domain.data.proximity.ProximityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = BaseResource.BASE_URL + MotherResource.RESOURCE_URL)
public class MotherResource extends BaseResource<Mother, MotherDto> {

    public static final String RESOURCE_URL = "/mothers";

    public MotherService motherService;

    public ProximityService proximityService;

    @Inject
    public MotherResource(MotherService motherService, ProximityService proximityService, MotherMapper motherMapper) {
        super(motherService, motherMapper, Mother.class, QMother.mother);
        this.motherService = motherService;
        this.proximityService = proximityService;
    }

    @GetMapping("/motherid")
    public Long motherId(){

        return motherService.getMotherId("SS1001-ui.txt", "-");

    }

    @GetMapping("/{id}/proximity-chart")
    public List<ProximityChart> getProximityChart(@PathVariable Long id){

        List<ProximityChart> result = proximityService.getProximityChart(id);

        return result;

    }


}
