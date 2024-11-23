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

    @PostMapping("/{patientId}/generate")
    public ResponseEntity<Prescription> generatePrescription(@PathVariable Long patientId, @RequestBody String diagnosis) {
        Prescription prescription = prescriptionService.generateAndSavePrescription(patientId, diagnosis);
        return ResponseEntity.ok(prescription);
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByPatient(patientId));
    }
}
