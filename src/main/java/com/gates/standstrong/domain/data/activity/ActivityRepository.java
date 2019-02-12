package com.gates.standstrong.domain.data.activity;

import com.gates.standstrong.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActivityRepository extends BaseRepository<Activity> {

    @Query(value = "SELECT DATE(capture_date) AS captureDate, COUNT(activity_type) as movementCount" +
            " FROM activity" +
            " WHERE mother_id = :motherId" +
            " AND activity_type NOT IN ('Still', 'Tilting', 'Unknown')" +
            " GROUP BY DATE(capture_date)", nativeQuery = true)
    List<Movement> getMovements(@Param("motherId") Long motherId);
}
