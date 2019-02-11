package com.gates.standstrong.domain.data.importfile;

import com.gates.standstrong.base.BaseService;

import java.util.List;

public interface ImportFileService extends BaseService<ImportFile> {

    List<ImportFile> getImportFilesByMotherId(Long motherId);

}
