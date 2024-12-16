package com.example.edapp.controller;

import com.example.edapp.model.Prescription;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.edapp.service.PrescriptionService;

import java.util.List;

@RestController
@RequestMapping("/prescriptions")
public class PrescriptionController {
    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @GetMapping("/{patientId}/generate")
    public ResponseEntity<String> generatePrescription(@PathVariable Long patientId) {
        // Fetch description from the database and generate prescription
        String prescription = prescriptionService.generatePrescription(patientId);
        return ResponseEntity.ok(prescription);
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByPatient(patientId));
    }
}
