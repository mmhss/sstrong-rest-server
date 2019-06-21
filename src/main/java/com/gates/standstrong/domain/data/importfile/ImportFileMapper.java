package com.gates.standstrong.domain.data.importfile;

import com.gates.standstrong.base.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImportFileMapper extends BaseMapper<ImportFileDto, ImportFile> {
}
