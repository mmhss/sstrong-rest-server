package com.gates.standstrong.domain.award;

import com.gates.standstrong.base.BaseService;
import com.gates.standstrong.domain.mother.Mother;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface AwardService extends BaseService<Award> {

    List<Award> getAwards(Long motherId, String awardType);

    boolean satisfiesNextBonusLevel(Long motherId, int nextBonusLevel);

    Award getTopAward(Long motherId, String awardType);

    boolean hasHighestAward(Mother mother, String awardType);

    Award getAnyAward(Long motherId, int nextBonusLevel);

    Award buildAward(Mother mother, String awardType, int awardLevel, LocalDate awardForDate);
}
