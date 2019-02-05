package com.gates.standstrong.domain.data.activity;

import com.gates.standstrong.base.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActivityMapper extends BaseMapper<ActivityDto, Activity> {
}
