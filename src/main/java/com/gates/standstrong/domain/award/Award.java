package com.gates.standstrong.domain.award;

import com.gates.standstrong.base.BaseEntity;
import com.gates.standstrong.domain.mother.Mother;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name="award")
public class Award extends BaseEntity {

    @Column(name="award_type")
    private String awardType;

    @Column(name="award_level")
    private int awardLevel;

    @Column(name = "award_for_date")
    private LocalDate awardForDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "mother_id")
    private Mother mother;
}
