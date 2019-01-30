package com.gates.standstrong.domain.data.importfile;

import com.gates.standstrong.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ImportFileServiceImpl extends BaseServiceImpl<ImportFile> implements ImportFileService {

    @Inject
    public ImportFileServiceImpl(ImportFileRepository importFileRepository){
        super(importFileRepository);
    }
}
