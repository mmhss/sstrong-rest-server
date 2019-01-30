package com.gates.standstrong.domain.project;


import com.gates.standstrong.base.BaseEntity;
import lombok.Data;
import com.gates.standstrong.domain.mother.Mother;
import com.gates.standstrong.domain.setting.Setting;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Data
@Entity
@Table(name="project")
public class Project extends BaseEntity {

    @Column(name="name")
    private String name;

    @Column(name="location")
    private String location;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "completion_date")
    private LocalDate completionDate;

    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL)
    private Setting setting;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Mother> mothers;

}
