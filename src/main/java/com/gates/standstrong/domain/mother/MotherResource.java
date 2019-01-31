package com.gates.standstrong.domain.mother;

import com.gates.standstrong.base.BaseResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping(value = BaseResource.BASE_URL + MotherResource.RESOURCE_URL)
public class MotherResource extends BaseResource<Mother, MotherDto> {

    public static final String RESOURCE_URL = "/mothers";

    public MotherService motherService;

    @Inject
    public MotherResource(MotherService motherService, MotherMapper motherMapper) {
        super(motherService, motherMapper, Mother.class, QMother.mother);
        this.motherService = motherService;
    }

    @GetMapping("/motherid")
    public Long motherId(){

        return motherService.getMotherId("SS1001-ui.txt", "-");

    }


}
