package com.gates.standstrong.domain.data.proximity;

import com.gates.standstrong.base.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProximityMapper extends BaseMapper<ProximityDto, Proximity> {
}
