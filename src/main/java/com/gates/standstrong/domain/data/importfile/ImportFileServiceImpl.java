package com.gates.standstrong.domain.data.importfile;

import com.gates.standstrong.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class ImportFileServiceImpl extends BaseServiceImpl<ImportFile> implements ImportFileService {

    private ImportFileRepository importFileRepository;

    @Inject
    public ImportFileServiceImpl(ImportFileRepository importFileRepository){
        super(importFileRepository);
        this.importFileRepository=importFileRepository;
    }

    @Override
    public List<ImportFile> getImportFilesByMotherId(Long motherId){

        return importFileRepository.getImportFilesByMotherId(motherId);

    }

}
