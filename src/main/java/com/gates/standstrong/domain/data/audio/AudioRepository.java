package com.gates.standstrong.domain.data.audio;

import com.gates.standstrong.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

interface AudioRepository extends BaseRepository<Audio> {

    @Query(value = "SELECT DISTINCT(date(capture_date)) as capture_date" +
            " FROM audio WHERE mother_id = :motherId" +
            " ORDER BY capture_date ", nativeQuery = true)
    List<Date> getAudioCapturedDates(@Param("motherId") Long motherId);


    @Query(value = "SELECT count(audio_type)" +
            " FROM Audio WHERE mother_id = :motherId" +
            " AND date(capture_date)=:awardForDate")
    Long getTalkCount(@Param("motherId") Long motherId, @Param("awardForDate") Date awardForDate);


    @Query(value = "SELECT DATE(capture_date) AS captureDate, COUNT(audio_type) as speechCount" +
            " FROM audio" +
            " WHERE mother_id = :motherId" +
            " AND audio_type = 'Speech'" +
            " GROUP BY DATE(capture_date)", nativeQuery = true)
    List<Speech> getSpeechCount(@Param("motherId") Long motherId);

}
