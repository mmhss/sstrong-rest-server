package com.gates.standstrong.domain.data.gps;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gates.standstrong.base.BaseDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GpsDto extends BaseDto {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime captureDate;

    private double latitude;
    private double longitude;
    private double altitude;
    private double accuracy;
    private BaseDto mother;

}