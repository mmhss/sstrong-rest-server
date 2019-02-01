package com.gates.standstrong.domain.data.audio;

import com.gates.standstrong.base.BaseResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping(value = BaseResource.BASE_URL + AudioResource.RESOURCE_URL)
public class AudioResource extends BaseResource<Audio, AudioDto> {

    public static final String RESOURCE_URL = "/audios";

    @Inject
    public AudioResource(AudioService audioService, AudioMapper audioMapper) {
        super(audioService, audioMapper, Audio.class, QAudio.audio);
    }
}
