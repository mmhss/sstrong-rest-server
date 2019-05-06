package com.gates.standstrong.domain.awardexecution;

import com.gates.standstrong.base.BaseService;

import java.sql.Date;
import java.util.List;

public interface AwardExecutionService extends BaseService<AwardExecution> {

    List<Date> getRanForCaptureDates(String awardDate, Long motherId);
}
