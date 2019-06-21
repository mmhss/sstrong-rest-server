package com.gates.standstrong.domain.data.importfile;

import com.gates.standstrong.base.BaseResource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping(value = BaseResource.BASE_URL + ImportFileResource.RESOURCE_URL)
public class ImportFileResource extends BaseResource<ImportFile, ImportFileDto> {

    public static final String RESOURCE_URL = "/importfiles";

    @Inject
    public ImportFileResource(ImportFileService importFileService, ImportFileMapper importFileMapper) {
        super(importFileService, importFileMapper, ImportFile.class, QImportFile.importFile);
    }

}
