package com.gates.standstrong.domain.award;

import com.gates.standstrong.base.BaseDto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AwardDto extends BaseDto {

    private String awardType;
    private int awardLevel;
    private LocalDate awardForDate;
    private BaseDto mother;
}
