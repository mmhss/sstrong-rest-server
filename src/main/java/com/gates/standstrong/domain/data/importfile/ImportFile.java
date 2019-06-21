package com.gates.standstrong.domain.data.importfile;

import com.gates.standstrong.base.BaseEntity;
import com.gates.standstrong.domain.mother.Mother;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "import_file")
public class ImportFile extends BaseEntity {

    @Column(name = "filename")
    private String filename;

    @Column(name = "status")
    private String status;

    @Column(name = "import_date")
    private LocalDateTime importDate;

    @Column(name = "fileType")
    private String fileType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mother_id")
    private Mother mother;
}
