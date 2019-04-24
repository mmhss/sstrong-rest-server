package com.gates.standstrong.domain.post;

import com.gates.standstrong.base.BaseEntity;
import com.gates.standstrong.domain.mother.Mother;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="post")
public class Post extends BaseEntity {

    @Column
    private String message;

    @Column(name = "posted_date")
    private LocalDateTime postedDate;

    @Column(name = "thread_id")
    private int threadId;

    @Column(name="direction")
    private String direction;

    @ManyToOne(optional = true)
    @JoinColumn(name = "mother_id")
    private Mother mother;
}