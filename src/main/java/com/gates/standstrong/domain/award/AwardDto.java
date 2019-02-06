package com.gates.standstrong.domain.award;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gates.standstrong.base.BaseDto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AwardDto extends BaseDto {

    private String awardType;
    private int awardLevel;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate awardForDate;
    private BaseDto mother;
}
