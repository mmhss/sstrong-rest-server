package com.gates.standstrong.domain.awardexecution;

import com.gates.standstrong.base.BaseEntity;
import com.gates.standstrong.domain.mother.Mother;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name="award_execution")
public class AwardExecution extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "mother_id")
    private Mother mother;

    @Column(name="award_type")
    private String awardType;

    @Column(name="ran_for_capture_date")
    private LocalDate ranForCaptureDate;

}
