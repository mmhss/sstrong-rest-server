package com.gates.standstrong.domain.data.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gates.standstrong.base.BaseDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityDto extends BaseDto {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime captureDate;

    private String activityType;
    private double confidence;
    private BaseDto mother;

}
