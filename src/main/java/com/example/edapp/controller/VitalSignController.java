package com.example.edapp.controller;

import com.example.edapp.controller.dto.VitalSignDTO;
import com.example.edapp.controller.dto.VitalSignRequest;
import com.example.edapp.model.VitalSign;
import com.example.edapp.service.MonitoringService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vitals")
public class VitalSignController {
    private final MonitoringService monitoringService;

    public VitalSignController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @PostMapping("/{patientId}")
    public ResponseEntity<?> recordVitalSigns(
            @PathVariable Long patientId,
            @RequestBody VitalSignRequest request
    ) {
        monitoringService.recordVitalSigns(patientId, request.getBloodPressure(), request.getHeartRate(), request.getOxygenSaturation());
        return ResponseEntity.ok("Vital signs recorded");
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<List<VitalSignDTO>> getVitalSigns(@PathVariable Long patientId) {
        List<VitalSign> vitalSigns = monitoringService.getPatientVitalSigns(patientId);
        List<VitalSignDTO> dtoList = vitalSigns.stream().map(VitalSignDTO::new).toList();
        return ResponseEntity.ok(dtoList);
    }
}

