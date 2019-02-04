package com.gates.standstrong.domain.data.audio;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gates.standstrong.base.BaseDto;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class AudioDto extends BaseDto {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime captureDate;
    private String audioType;
    private String filename;
    private BaseDto mother;

}
