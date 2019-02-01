package com.gates.standstrong.domain.data.audio;

import com.gates.standstrong.base.mapper.BaseMapper;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface AudioMapper extends BaseMapper<AudioDto, Audio> {
}
