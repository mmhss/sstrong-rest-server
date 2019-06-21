package com.gates.standstrong.domain.data.importfile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gates.standstrong.base.BaseDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ImportFileDto extends BaseDto {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime importDate;
    private String filename;
    private String status;
    private String fileType;
    private BaseDto mother;

}
