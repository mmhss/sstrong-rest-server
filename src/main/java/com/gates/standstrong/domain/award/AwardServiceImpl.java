package com.gates.standstrong.domain.award;

import com.gates.standstrong.base.BaseServiceImpl;
import com.gates.standstrong.domain.mother.Mother;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

@Service
public class AwardServiceImpl extends BaseServiceImpl<Award> implements AwardService {

    private AwardRepository awardRepository;

    @Inject
    public AwardServiceImpl(AwardRepository awardRepository) {
        super(awardRepository);
        this.awardRepository = awardRepository;
    }

    @Override
    public List<Award> getAwards(Long motherId, String awardType) {
        return awardRepository.getAwards(motherId, awardType);
    }

    @Override
    public boolean satisfiesNextBonusLevel(Long motherId, int nextBonusLevel) {
        int result = awardRepository.satisfiesNextBonusLevel(motherId, nextBonusLevel);

        return result == 1 ? true : false;
    }

    @Override
    public Award getTopAward(Long motherId, String awardType) {
        return awardRepository.getTopAward(motherId, awardType);
    }

    @Override
    public boolean hasHighestAward(Mother mother, String awardType) {
        if (this.getTopAward(mother.getId(), awardType) != null && this.getTopAward(mother.getId(), awardType).getAwardLevel() == AwardConstants.AWARD_LEVEL_THREE) {
            return true;
        }
        return false;
    }

    @Override
    public Award getAnyAward(Long motherId, int level) {
        return awardRepository.getAnyAward(motherId, level);
    }

    @Override
    public Award buildAward(Mother mother, String awardType, int awardLevel, LocalDate awardForDate) {

        Award award = new Award();
        award.setAwardLevel(awardLevel);
        award.setAwardForDate(awardForDate);
        award.setAwardType(awardType);
        award.setMother(mother);

        return award;
    }

}
