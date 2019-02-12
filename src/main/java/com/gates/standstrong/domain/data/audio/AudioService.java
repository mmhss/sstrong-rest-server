package com.gates.standstrong.domain.data.audio;

import com.gates.standstrong.base.BaseService;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface AudioService  extends BaseService<Audio> {

    List<Date> getAudioCapturedDates(Long motherId);

    Long getTalkCount(Long motherId, Date awardForDate);

    List<Speech> getSpeeches(Long motherId);
}
