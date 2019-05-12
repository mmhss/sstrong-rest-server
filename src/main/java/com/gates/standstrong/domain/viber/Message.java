package com.gates.standstrong.domain.viber;

import com.gates.standstrong.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="message")
@Data
public class Message extends BaseEntity {

    @Column(name="callback")
    private String callback;
}
