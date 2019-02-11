package com.gates.standstrong.domain.award;

import com.gates.standstrong.base.BaseServiceImpl;
import com.gates.standstrong.domain.mother.Mother;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
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
    public Award getTopAward(Long motherId, String awardType) {
        return awardRepository.getTopAward(motherId, awardType);
    }

    @Override
    public boolean hasHighestAward(Mother mother, String awardType) {
        if(this.getTopAward(mother.getId(), awardType)!=null && this.getTopAward(mother.getId(), awardType).getAwardLevel() == AwardConstants.AWARD_LEVEL_THREE ){
            return true;
        }
        return false;
    }
}
