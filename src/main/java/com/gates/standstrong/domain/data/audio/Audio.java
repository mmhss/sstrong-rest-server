package com.gates.standstrong.domain.data.audio;

import com.gates.standstrong.base.BaseEntity;
import com.gates.standstrong.domain.data.importfile.ImportFile;
import com.gates.standstrong.domain.mother.Mother;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name="audio")
public class Audio extends BaseEntity {

    @Column(name="capture_date")
    private LocalDate captureDate;

    @Column
    private String type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "import_file_id")
    private ImportFile importFile;

    @ManyToOne(optional = false)
    @JoinColumn(name = "mother_id")
    private Mother mother;

}
