package com.gates.standstrong.domain.award;

import com.gates.standstrong.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class AwardServiceImpl extends BaseServiceImpl<Award> implements AwardService {

    private AwardRepository awardRepository;

    @Inject
    public AwardServiceImpl(AwardRepository awardRepository) {
        super(awardRepository);
    }
}
