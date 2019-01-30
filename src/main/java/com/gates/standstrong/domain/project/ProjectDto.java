package com.gates.standstrong.domain.project;

import com.gates.standstrong.base.BaseDto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectDto extends BaseDto {

    private String name;
    private String location;
    private LocalDate startDate;
    private LocalDate completionDate;

}
