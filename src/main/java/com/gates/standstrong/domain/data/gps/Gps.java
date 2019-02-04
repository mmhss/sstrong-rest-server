package com.gates.standstrong.domain.data.gps;

import com.gates.standstrong.base.BaseEntity;
import lombok.Data;
import com.gates.standstrong.domain.mother.Mother;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="gps")
public class Gps extends BaseEntity {

    @Column(name="capture_date")
    private LocalDateTime captureDate;

    @Column(name="longitude")
    private double longitude;

    @Column(name="latitude")
    private double latitude;

    @Column(name="altitude")
    private double altitude;

    @Column(name="accuracy")
    private double accuracy;

    @ManyToOne(optional = false)
    @JoinColumn(name = "mother_id")
    private Mother mother;

}
