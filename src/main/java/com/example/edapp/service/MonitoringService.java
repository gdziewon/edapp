package com.example.edapp.service;

import com.example.edapp.model.Patient;
import com.example.edapp.model.VitalSign;
import com.example.edapp.repository.VitalSignRepository;
import com.example.edapp.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class MonitoringService {
    private final VitalSignRepository vitalSignRepository;
    private final PatientRepository patientRepository;

    public MonitoringService(VitalSignRepository vitalSignRepository, PatientRepository patientRepository) {
        this.vitalSignRepository = vitalSignRepository;
        this.patientRepository = patientRepository;
    }

    public void recordVitalSigns(Long patientId, String bloodPressure, int heartRate, int oxygenSaturation) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));
        VitalSign vitalSign = new VitalSign(patient, bloodPressure, heartRate, oxygenSaturation);
        vitalSignRepository.save(vitalSign);
    }


    public List<VitalSign> getPatientVitalSigns(Long patientId) {
        return vitalSignRepository.findByPatientIdOrderByRecordedAtDesc(patientId);
    }

    public List<VitalSign> getVitalSignsByDateRange(Long patientId, Instant start, Instant end) {
        return vitalSignRepository.findByPatientIdAndRecordedAtBetween(patientId, start, end);
    }

}
