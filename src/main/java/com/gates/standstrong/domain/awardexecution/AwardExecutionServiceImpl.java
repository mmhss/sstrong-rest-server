package com.gates.standstrong.domain.awardexecution;

import com.gates.standstrong.base.BaseServiceImpl;
import com.gates.standstrong.domain.data.importfile.ImportFile;
import com.gates.standstrong.domain.data.importfile.ImportFileService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AwardExecutionServiceImpl extends BaseServiceImpl<AwardExecution> implements AwardExecutionService {

    private AwardExecutionRepository awardExecutionRepository;

    private ImportFileService importFileService;

    @Inject
    public AwardExecutionServiceImpl(ImportFileService importFileService, AwardExecutionRepository awardExecutionRepository) {
        super(awardExecutionRepository);
        this.importFileService = importFileService;
        this.awardExecutionRepository = awardExecutionRepository;
    }

    @Override
    public List<Date> getRanForCaptureDates(String awardType, Long motherId) {

        List<Date> ranForCaptureDates = awardExecutionRepository.getRanForCaptureDates(awardType, motherId);

        return ranForCaptureDates;
    }
}

