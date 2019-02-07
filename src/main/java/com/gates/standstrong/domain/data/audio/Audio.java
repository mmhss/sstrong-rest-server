package com.gates.standstrong.domain.data.audio;

import com.gates.standstrong.base.BaseEntity;
import com.gates.standstrong.domain.mother.Mother;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="audio")
public class Audio extends BaseEntity {

    @Column(name="capture_date")
    private LocalDateTime captureDate;

    @Column(name="audio_type")
    private String audioType;

    @Column
    private Double accuracy;

    @Column
    private String filename;

    @ManyToOne(optional = false)
    @JoinColumn(name = "mother_id")
    private Mother mother;

}
