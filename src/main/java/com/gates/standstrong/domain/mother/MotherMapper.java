package com.gates.standstrong.domain.mother;

import com.gates.standstrong.base.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MotherMapper extends BaseMapper<MotherDto, Mother> {
}

