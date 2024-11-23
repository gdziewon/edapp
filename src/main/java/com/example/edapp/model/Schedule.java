package com.example.edapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "staff_name")
    private String staffName;

    @Column(name = "role", length = 50)
    private String role;

    @Column(name = "shift_start")
    private Instant shiftStart;

    @Column(name = "shift_end")
    private Instant shiftEnd;

    public Schedule(String staffName, String role, Instant shiftStart, Instant shiftEnd) {
        this.staffName = staffName;
        this.role = role;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
    }
}