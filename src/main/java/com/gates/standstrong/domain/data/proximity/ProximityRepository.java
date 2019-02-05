package com.gates.standstrong.domain.data.proximity;

import com.gates.standstrong.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface ProximityRepository extends BaseRepository<Proximity> {

    @Query(value="SELECT DATE(capture_date) AS chartDay" +
            ", HOUR(capture_date) as chartHour" +
            ", event as chartEvent" +
            ", value as chartValue" +
            " FROM proximity " +
            " INNER JOIN mother on proximity.mother_id = mother.id" +
            " WHERE mother.id = :id" +
            " AND event='Monitor' AND value=1" +
            " GROUP BY chartDay, chartHour, chartEvent, chartValue" +
            " UNION " +
            " SELECT DATE(capture_date) AS chartDay" +
            ", HOUR(capture_date) as chartHour" +
            ", event as chartEvent" +
            ", value as chartValue " +
            " FROM proximity" +
            " INNER JOIN mother on proximity.mother_id = mother.id" +
            " WHERE mother.id = :id" +
            " AND event='Visibility' AND value=1" +
            " GROUP BY chartDay, chartHour, chartEvent, chartValue" +
            " ORDER  BY  chartDay, chartHour, chartEvent, chartValue;", nativeQuery = true)
    List<ProximityChart> getProximityChart(@Param("id") Long id);
}
