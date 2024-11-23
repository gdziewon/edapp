package com.example.edapp.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VitalSignRequest {
    private String bloodPressure;
    private int heartRate;
    private int oxygenSaturation;
}
