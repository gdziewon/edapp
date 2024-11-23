package com.example.edapp.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PatientRequest {
    private String name;
    private int age;
    private String conditionDescription;
    private String bloodPressure;
    private int heartRate;
    private int oxygenSaturation;
}
