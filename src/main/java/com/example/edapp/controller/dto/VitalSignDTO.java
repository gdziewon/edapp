package com.example.edapp.controller.dto;

import com.example.edapp.model.VitalSign;

public class VitalSignDTO {
    private String bloodPressure;
    private int heartRate;
    private int oxygenSaturation;

    private String patientName;
    private int patientAge;

    public VitalSignDTO(VitalSign vitalSign) {
        this.bloodPressure = vitalSign.getBloodPressure();
        this.heartRate = vitalSign.getHeartRate();
        this.oxygenSaturation = vitalSign.getOxygenSaturation();
        this.patientName = vitalSign.getPatient().getName();
        this.patientAge = vitalSign.getPatient().getAge();
    }
}
