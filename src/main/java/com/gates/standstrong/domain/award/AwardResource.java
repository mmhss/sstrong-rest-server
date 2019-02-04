package com.gates.standstrong.domain.award;

import com.gates.standstrong.base.BaseResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping(value = BaseResource.BASE_URL + AwardResource.RESOURCE_URL)
public class AwardResource extends BaseResource<Award, AwardDto> {

    public static final String RESOURCE_URL = "/awards";

    @Inject
    public AwardResource(AwardService awardService, AwardMapper awardMapper) {
        super(awardService, awardMapper, Award.class, QAward.award);
    }
}
