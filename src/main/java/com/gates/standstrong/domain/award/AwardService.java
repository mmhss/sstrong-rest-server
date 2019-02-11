package com.gates.standstrong.domain.award;

import com.gates.standstrong.base.BaseService;
import com.gates.standstrong.domain.mother.Mother;

import java.util.List;

public interface AwardService extends BaseService<Award> {

    List<Award> getAwards(Long motherId, String awardType);

    Award getTopAward(Long motherId, String awardType);

    boolean hasHighestAward(Mother mother, String awardType);
}
