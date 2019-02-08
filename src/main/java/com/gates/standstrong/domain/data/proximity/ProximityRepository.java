package com.gates.standstrong.domain.data.proximity;

import com.gates.standstrong.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface ProximityRepository extends BaseRepository<Proximity> {

    @Query(value="SELECT proximity.id as proximityId, DATE(capture_date) AS chartDay" +
            ", HOUR(capture_date) as chartHour" +
            ", event as chartEvent" +
            ", value as chartValue" +
            ", mother.id as motherId" +
            " FROM proximity " +
            " INNER JOIN mother on proximity.mother_id = mother.id" +
            " WHERE mother.id = :motherId" +
            " AND event='Monitor' AND value=1" +
            " GROUP BY chartDay, chartHour, chartEvent, chartValue" +
            " UNION " +
            " SELECT proximity.id as proximityId, DATE(capture_date) AS chartDay" +
            ", HOUR(capture_date) as chartHour" +
            ", event as chartEvent" +
            ", value as chartValue " +
            ", mother.id as motherId" +
            " FROM proximity" +
            " INNER JOIN mother on proximity.mother_id = mother.id" +
            " WHERE mother.id = :motherId" +
            " AND event='Visibility' AND value=1" +
            " GROUP BY chartDay, chartHour, chartEvent, chartValue" +
            " ORDER  BY  chartDay, chartHour, chartEvent, chartValue;", nativeQuery = true)
    List<ProximityChart> getProximityChart(@Param("motherId") Long motherId);


    @Query(value="SELECT proximity.id as proximityId" +
            ", DATE(capture_date) AS chartDay" +
            ", HOUR(capture_date) as chartHour" +
            ", event as chartEvent" +
            ", value as chartValue" +
            ", mother.id as motherId" +
            " FROM proximity " +
            " INNER JOIN mother on proximity.mother_id = mother.id" +
            " WHERE proximity.id > :pId" +
            " AND event='Monitor' AND value=1" +
            " GROUP BY chartDay, chartHour, chartEvent, chartValue, motherId" +
            " UNION " +
            " SELECT proximity.id as proximityId" +
            ", DATE(capture_date) AS chartDay" +
            ", HOUR(capture_date) as chartHour" +
            ", event as chartEvent" +
            ", value as chartValue " +
            ", mother.id as motherId" +
            " FROM proximity" +
            " INNER JOIN mother on proximity.mother_id = mother.id" +
            " WHERE proximity.id > :pId" +
            " AND event='Visibility' AND value=1" +
            " GROUP BY chartDay, chartHour, chartEvent, chartValue, motherId" +
            " ORDER  BY chartDay, chartHour, chartEvent, motherId;", nativeQuery = true)
    List<ProximityChart> getProximityCharts(@Param("pId") Long proximitySyncId);
}
