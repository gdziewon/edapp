package com.example.edapp.service;

import com.example.edapp.model.Doctor;
import com.example.edapp.model.Patient;
import com.example.edapp.repository.DoctorRepository;
import com.example.edapp.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RegistrationService {
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final QueueService queueService;

    public RegistrationService(PatientRepository patientRepository, DoctorRepository doctorRepository, QueueService queueService) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.queueService = queueService;
    }

    public Patient registerPatient(String name, int age, String conditionDescription, Map<String, String> parameters) {
        int priority = calculateTriagePriority(parameters);
        Doctor assignedDoctor = assignDoctor(conditionDescription);

        Patient patient = new Patient(name, age, conditionDescription, priority, assignedDoctor.getId());
        return patientRepository.save(patient);
    }

    private int calculateTriagePriority(Map<String, String> parameters) {
        int priorityScore = 0;

        if ("high".equalsIgnoreCase(parameters.get("bloodPressure"))) priorityScore += 2;
        if (Integer.parseInt(parameters.get("heartRate")) > 120) priorityScore += 3;
        if (Integer.parseInt(parameters.get("oxygenSaturation")) < 90) priorityScore += 3;
        if ("low".equalsIgnoreCase(parameters.get("bloodPressure"))) priorityScore += 1;

        String condition = parameters.getOrDefault("conditionDescription", "").toLowerCase();
        if (condition.contains("critical")) priorityScore += 3;
        if (Integer.parseInt(parameters.get("age")) > 65 || Integer.parseInt(parameters.get("age")) < 12) priorityScore += 2;

        // Translate score to priority
        if (priorityScore >= 7) return 1; // Critical
        if (priorityScore >= 4) return 2; // Moderate
        return 3; // Stable
    }

    private Doctor assignDoctor(String conditionDescription) {
        String specialization = determineSpecialization(conditionDescription);
        List<Doctor> availableDoctors = doctorRepository.findBySpecialization(specialization);

        if (availableDoctors.isEmpty()) {
            availableDoctors = doctorRepository.findBySpecialization("General Medicine");
        }

        if (availableDoctors.isEmpty()) {
            throw new RuntimeException("No doctors available for specialization: " + specialization);
        }

        return availableDoctors.stream()
                .min((d1, d2) -> {
                    int queueSize1 = queueService.getQueueForDoctor(d1.getId()).size();
                    int queueSize2 = queueService.getQueueForDoctor(d2.getId()).size();
                    return Integer.compare(queueSize1, queueSize2);
                })
                .orElseThrow(() -> new RuntimeException("Unable to find a doctor with the smallest queue."));
    }

    private String determineSpecialization(String conditionDescription) {
        if (conditionDescription.toLowerCase().contains("heart")) return "Cardiology";
        if (conditionDescription.toLowerCase().contains("brain")) return "Neurology";
        if (conditionDescription.toLowerCase().contains("diagnostic")) return "Diagnostics";
        return "General Medicine";
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Page<Patient> getPatients(PageRequest of) {
        return patientRepository.findAll(of);
    }
}

