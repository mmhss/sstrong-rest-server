package com.gates.standstrong.domain.data.proximity;

import com.gates.standstrong.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
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



    @Query(value = "SELECT t1.proximityId, t1.chartDay, t1.chartHour, t1.chartEvent, t1.chartValue, t1.motherId" +
            " From " +
            " ( SELECT proximity.id as proximityId" +
            "       , DATE(capture_date) AS chartDay" +
            "       , HOUR(capture_date) as chartHour" +
            "       , event as chartEvent" +
            "       , value as chartValue" +
            "       , mother.id as motherId" +
            "       FROM proximity" +
            "       INNER JOIN mother on proximity.mother_id = mother.id" +
            "       WHERE proximity.id > :pId" +
            "       AND event='Visibility' AND value=0" +
            "       GROUP BY chartDay, chartHour, mother_id) AS t1" +
            " LEFT JOIN" +
            " ( SELECT proximity.id as proximityId" +
            "       , DATE(capture_date) AS chartDay" +
            "       , HOUR(capture_date) as chartHour" +
            "       , event as chartEvent" +
            "       , value as chartValue" +
            "       , mother.id as motherId" +
            "       FROM proximity" +
            "       INNER JOIN mother on proximity.mother_id = mother.id" +
            "       WHERE proximity.id > :pId" +
            "       AND event='Visibility' AND value=1" +
            "       GROUP BY chartDay, chartHour, mother_id) as t2" +
            "  ON t1.chartDay = t2.chartDay and t1.chartHour = t2.chartHour" +
            "  WHERE t2.chartHour IS NULL" +
            "  UNION" +
            "  SELECT proximity.id as proximityId" +
            "       , DATE(capture_date) AS chartDay" +
            "       , HOUR(capture_date) as chartHour" +
            "       , event as chartEvent" +
            "       , value as chartValue" +
            "       , mother_id as motherId" +
            "  FROM proximity" +
            "  INNER JOIN mother ON proximity.mother_id = mother.id" +
            " WHERE proximity.id > :pId" +
            "       AND event='Visibility' AND value=1" +
            "  GROUP BY chartDay, chartHour, mother_id" +
            "  ORDER BY proximityId asc" +
            "  LIMIT :limit", nativeQuery = true)
    List<ProximityChart> getProximityCharts(@Param("pId") Long proximitySyncId, @Param("limit") Long limit);

    @Query(value="SELECT DISTINCT t1.chartDay AS chartDay, count(t1.chartDay) AS hourCount" +
            " FROM (" +
            " SELECT DATE(capture_date) AS chartDay" +
            ", HOUR(capture_date) AS chartHour" +
            ", event AS chartEvent" +
            ", value AS chartValue" +
            " FROM proximity" +
            " INNER JOIN mother ON proximity.mother_id = mother.id" +
            " WHERE mother.id = :motherId" +
            " AND event='Visibility' AND value=0" +
            " GROUP BY chartDay, chartHour, chartEvent, chartValue) AS t1" +
            " LEFT JOIN " +
            " (SELECT DATE(capture_date) AS chartDay" +
            ", HOUR(capture_date) AS chartHour" +
            ", event AS chartEvent" +
            ", value AS chartValue" +
            " FROM proximity" +
            " INNER JOIN mother ON proximity.mother_id = mother.id" +
            " WHERE mother.id = :motherId" +
            " AND event='Visibility' AND value=1" +
            " GROUP BY chartDay, chartHour, chartEvent, chartValue) AS t2" +
            " ON t1.chartDay = t2.chartDay AND t1.chartHour = t2.chartHour" +
            " WHERE t2.chartHour IS NULL" +
            " GROUP BY t1.chartDay" +
            " ORDER BY t1.chartDay ASC", nativeQuery = true)
    List<SelfCare> getSelfCaredDays(@Param("motherId") Long motherId);



    @Query(value = "SELECT t1.chartDay, t1.chartHour, t1.chartEvent, t1.chartValue, t1.motherId" +
            " From " +
            " ( SELECT DATE(capture_date) AS chartDay" +
            "       , HOUR(capture_date) as chartHour" +
            "       , event as chartEvent" +
            "       , value as chartValue" +
            "       , mother.id as motherId" +
            "       FROM proximity" +
            "       INNER JOIN mother on proximity.mother_id = mother.id" +
            "       WHERE mother.id = :motherId" +
            "       AND event='Visibility' AND value=0" +
            "       GROUP BY chartDay, chartHour, mother_id) AS t1" +
            " LEFT JOIN" +
            " ( SELECT DATE(capture_date) AS chartDay" +
            "       , HOUR(capture_date) as chartHour" +
            "       , event as chartEvent" +
            "       , value as chartValue" +
            "       , mother.id as motherId" +
            "       FROM proximity" +
            "       INNER JOIN mother on proximity.mother_id = mother.id" +
            "       WHERE mother.id = :motherId" +
            "       AND event='Visibility' AND value=1" +
            "       GROUP BY chartDay, chartHour, mother_id) as t2" +
            "  ON t1.chartDay = t2.chartDay and t1.chartHour = t2.chartHour" +
            "  WHERE t2.chartHour IS NULL" +
            "  UNION" +
            "  SELECT DATE(capture_date) AS chartDay" +
            "       , HOUR(capture_date) as chartHour" +
            "       , event as chartEvent" +
            "       , value as chartValue" +
            "       , mother_id as motherId" +
            "  FROM proximity" +
            "  INNER JOIN mother ON proximity.mother_id = mother.id" +
            " WHERE mother.id = :motherId" +
            "       AND event='Visibility' AND value=1" +
            "  GROUP BY chartDay, chartHour" +
            "  ORDER BY chartDay, chartHour", nativeQuery = true)
    List<ProximityChart> getProximityChartsByMother(@Param("motherId") Long motherId);

    @Query(value = "SELECT DISTINCT DATE(capture_date) AS chartDay" +
            "       FROM proximity" +
            "       INNER JOIN mother on proximity.mother_id = mother.id" +
            "       WHERE mother.id = :motherId" +
            "       ORDER BY chartDay", nativeQuery = true)
    List<Date> getDates(@Param("motherId") Long motherId);
}
