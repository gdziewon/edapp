package com.example.edapp.controller;

import com.example.edapp.controller.dto.PatientRequest;
import com.example.edapp.model.Patient;
import com.example.edapp.service.QueueService;
import com.example.edapp.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private final RegistrationService registrationService;
    private final QueueService queueService;

    public PatientController(RegistrationService registrationService, QueueService queueService) {
        this.registrationService = registrationService;
        this.queueService = queueService;
    }

    @PostMapping("/register")
    public ResponseEntity<Patient> registerPatient(@RequestBody PatientRequest request) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("bloodPressure", request.getBloodPressure());
        parameters.put("heartRate", String.valueOf(request.getHeartRate()));
        parameters.put("oxygenSaturation", String.valueOf(request.getOxygenSaturation()));
        parameters.put("age", String.valueOf(request.getAge())); // Added
        parameters.put("conditionDescription", request.getConditionDescription()); // Added

        Patient patient = registrationService.registerPatient(
                request.getName(),
                request.getAge(),
                request.getConditionDescription(),
                parameters
        );
        queueService.addPatientToQueue(patient.getDoctorAssignedId(), patient);
        return ResponseEntity.ok(patient);
    }

    @GetMapping("/queue/{doctorId}")
    public ResponseEntity<List<Patient>> getDoctorQueue(@PathVariable Long doctorId) {
        return ResponseEntity.ok(new ArrayList<>(queueService.getQueueForDoctor(doctorId)));
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        Page<Patient> patients = registrationService.getPatients(PageRequest.of(page, size));
        return ResponseEntity.ok(patients.getContent());
    }

}

