package com.gates.standstrong.domain.data.audio;

import com.gates.standstrong.base.BaseServiceImpl;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Date;
import java.util.List;

@Service
public class AudioServiceImpl extends BaseServiceImpl<Audio> implements AudioService {

    private AudioRepository audioRepository;

    @Inject
    public AudioServiceImpl(AudioRepository audioRepository) {
        super(audioRepository);
        this.audioRepository = audioRepository;
    }

    @Override
    public List<Date> getAudioCapturedDates(Long motherId) {
        return audioRepository.getAudioCapturedDates(motherId);
    }

    @Override
    public Long getTalkCount(Long motherId, Date awardForDate) {
        return audioRepository.getTalkCount(motherId, awardForDate);
    }

    @Override
    public List<Speech> getSpeechCount(Long motherId){
        return audioRepository.getSpeechCount(motherId);
    }
}
