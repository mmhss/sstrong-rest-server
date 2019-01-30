package com.gates.standstrong.domain.setting;


import com.gates.standstrong.base.BaseEntity;
import lombok.Data;
import com.gates.standstrong.domain.project.Project;

import javax.persistence.*;


@Data
@Entity
@Table(name="setting")
public class Setting extends BaseEntity {

    @Column(name="inbound_folder")
    private String inboundFolder;

    @Column(name="backup_folder")
    private String backupFolder;

    @OneToOne(optional = false)
    @JoinColumn(name = "project_id")
    private Project project;
}
