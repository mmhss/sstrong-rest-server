package com.gates.standstrong.domain.mother;

import com.gates.standstrong.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.inject.Inject;

@Service
public class MotherServiceImpl extends BaseServiceImpl<Mother> implements MotherService {

    private MotherRepository motherRepository;

    @Inject
    public MotherServiceImpl(MotherRepository motherRepository){
        super(motherRepository);
        this.motherRepository = motherRepository;
    }


    @Override
    public Long getMotherId(String filename, String delimiter) {

        final String[] parts = StringUtils.split(filename, delimiter);

        if (parts.length > 0) {
            return motherRepository.findId(parts[0]);
        }

        return null;
    }

    @Override
    public Mother findMother(String viberId) {
        return motherRepository.findMother(viberId);
    }


}

