package com.gates.standstrong.domain.awardexecution;

import com.gates.standstrong.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

interface AwardExecutionRepository extends BaseRepository<AwardExecution> {

    @Query(value="SELECT ran_for_capture_date FROM award_execution WHERE award_type = :awardType AND mother_id=:motherId", nativeQuery = true)
    List<Date> getRanForCaptureDates(@Param("awardType")String awardType, @Param("motherId") Long motherId);

}
