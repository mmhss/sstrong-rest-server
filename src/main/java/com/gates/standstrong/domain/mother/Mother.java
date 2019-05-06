package com.gates.standstrong.domain.mother;

import com.gates.standstrong.base.BaseEntity;
import lombok.Data;
import com.gates.standstrong.domain.project.Project;

import javax.persistence.*;

@Data
@Entity
@Table(name="mother")
public class Mother extends BaseEntity {

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="middle_name")
    private String middleName;

    @Column(name="identification_number")
    private String identificationNumber;

    @Column(name="age")
    private int age;

    @Column(name="street_address")
    private String streetAddress;

    @Column(name="city")
    private String city;

    @Column(name="state")
    private String state;

    @Column(name="postal_code")
    private String postalCode;

    @Column(name="country")
    private String country;

    @Column(name="status")
    private String status;

    @Column(name="viber_id")
    private String viberId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id")
    private Project project;

}
