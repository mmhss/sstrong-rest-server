package com.gates.standstrong.domain.mother;

import com.gates.standstrong.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class MotherServiceImpl extends BaseServiceImpl<Mother> implements MotherService {

    @Inject
    public MotherServiceImpl(MotherRepository motherRepository){
        super(motherRepository);
    }
}
