package com.gates.standstrong.domain.data.audio;

import com.gates.standstrong.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class AudioServiceImpl extends BaseServiceImpl<Audio> implements AudioService {

    private AudioRepository audioRepository;

    @Inject
    public AudioServiceImpl(AudioRepository audioRepository) {
        super(audioRepository);
    }
}
