package com.gates.standstrong.domain.post;

import com.gates.standstrong.base.mapper.BaseMapper;
import com.gates.standstrong.base.mapper.ReferenceMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses={ReferenceMapper.class})
public interface PostMapper extends BaseMapper<PostDto, Post> {
}
