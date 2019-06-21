package com.gates.standstrong.domain.data.importfile;

import com.gates.standstrong.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface ImportFileRepository extends BaseRepository<ImportFile> {

    @Query(value = "SELECT i FROM ImportFile i WHERE i.mother.id = :motherId")
    List<ImportFile> getImportFilesByMotherId(@Param("motherId") Long motherId);

}
