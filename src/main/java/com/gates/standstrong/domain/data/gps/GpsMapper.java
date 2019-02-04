package com.gates.standstrong.domain.data.gps;

import com.gates.standstrong.base.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GpsMapper extends BaseMapper<GpsDto, Gps> {
}
