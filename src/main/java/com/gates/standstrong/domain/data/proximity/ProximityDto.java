package com.gates.standstrong.domain.data.proximity;

import com.gates.standstrong.base.BaseDto;
import lombok.Data;
import com.gates.standstrong.domain.mother.Mother;

@Data
public class ProximityDto extends BaseDto {

    private String captureDate;
    private String androidId;
    private String event;
    private int value;
    private BaseDto mother;

}
