package com.gates.standstrong.domain.data.activity;

import com.gates.standstrong.base.BaseEntity;
import com.gates.standstrong.domain.mother.Mother;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="activity")
public class Activity extends BaseEntity {

    @Column(name="capture_date")
    private LocalDateTime captureDate;

    @Column(name="activity_type")
    private String activityType;

    @Column
    private double confidence;

    @ManyToOne(optional = false)
    @JoinColumn(name = "mother_id")
    private Mother mother;

}
