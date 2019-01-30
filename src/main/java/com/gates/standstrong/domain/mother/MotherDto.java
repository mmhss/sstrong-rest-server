package com.gates.standstrong.domain.mother;

import com.gates.standstrong.base.BaseDto;
import lombok.Data;

@Data
public class MotherDto extends BaseDto {

    private String identificationNumber;

    private String status;

    private BaseDto project;

}
