package com.gates.standstrong.domain.mother;

import com.gates.standstrong.base.BaseService;

public interface MotherService extends BaseService<Mother> {

    Long getMotherId(String filename, String delimiter);

    Mother findMother(String viberId);
}
