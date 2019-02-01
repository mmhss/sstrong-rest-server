package com.gates.standstrong.domain.data.audio;

import com.gates.standstrong.base.BaseDto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AudioDto extends BaseDto {

    private LocalDate captureDate;
    private String type;
    private BaseDto importFile;
    private BaseDto mother;

}
