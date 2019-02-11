package com.gates.standstrong.domain.award;

import com.gates.standstrong.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface AwardRepository extends BaseRepository<Award> {

    @Query(value = "SELECT * FROM award WHERE mother_id=:motherId and award_type=:awardType", nativeQuery = true)
    List<Award> getAwards(@Param("motherId") Long motherId, @Param("awardType") String awardType);

    @Query(value = "SELECT * FROM award WHERE mother_id=:motherId AND award_type=:awardType ORDER BY award_level DESC LIMIT 1", nativeQuery = true)
    Award getTopAward(@Param("motherId") Long motherId, @Param("awardType") String awardType);

}
