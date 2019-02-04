package com.gates.standstrong.domain.award;

import com.gates.standstrong.base.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AwardMapper extends BaseMapper<AwardDto, Award> {
}
