package com.example.edapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Lob
    @Column(name = "condition_description")
    private String conditionDescription;

    @Column(name = "triage_priority")
    private Integer triagePriority;

    @Column(name = "doctor_assigned_id")
    private Long doctorAssignedId;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "registration_time")
    private Instant registrationTime;

    @PrePersist
    public void prePersist() {
        this.registrationTime = Instant.now();
    }

    public Patient(String name, int age, String conditionDescription, int priority, Long doctorAssignedId) {
        this.name = name;
        this.age = age;
        this.conditionDescription = conditionDescription;
        this.triagePriority = priority;
        this.doctorAssignedId = doctorAssignedId;
        this.registrationTime = Instant.now();
    }
}