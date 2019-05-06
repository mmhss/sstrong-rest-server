package com.gates.standstrong.domain.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gates.standstrong.base.BaseDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto extends BaseDto {


    private String message;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime postedDate;

    private int threadId;
    private String direction;
    private String status;
    private String media;

    private BaseDto mother;
}
