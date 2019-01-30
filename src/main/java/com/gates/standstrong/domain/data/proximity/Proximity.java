package com.gates.standstrong.domain.data.proximity;

import com.gates.standstrong.base.BaseEntity;
import lombok.Data;
import com.gates.standstrong.domain.mother.Mother;

import javax.persistence.*;

@Data
@Entity
@Table(name="proximity")
public class Proximity extends BaseEntity {

    @Column(name="capture_date")
    private String captureDate;

    @Column(name="android_id")
    private String androidId;

    @Column(name="event")
    private String event;

    @Column(name="value")
    private int value;

    @ManyToOne(optional = false)
    @JoinColumn(name = "mother_id")
    private Mother mother;
}
