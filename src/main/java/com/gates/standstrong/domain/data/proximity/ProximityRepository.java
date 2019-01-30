package com.gates.standstrong.domain.data.proximity;

import com.gates.standstrong.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface ProximityRepository extends BaseRepository<Proximity> {

    @Query(value="SELECT DATE(capture_date) AS aDay" +
            ", HOUR(capture_date) as anHour" +
            ", event" +
            ", value" +
            " FROM proximity " +
            " INNER JOIN mother on proximity.mother_id = mother.id" +
            " WHERE mother.identification_number = :identificationNumber" +
            " AND event='Visibility'" +
            " GROUP BY aDay, anHour, event, value " +
            " ORDER BY aDay, anHour, event, value;", nativeQuery = true)
    List<Object[]> getProximityChart(@Param("identificationNumber") String identificationNumber);
}
