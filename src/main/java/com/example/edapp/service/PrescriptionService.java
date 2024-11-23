package com.example.edapp.service;

import com.example.edapp.model.Patient;
import com.example.edapp.model.Prescription;
import com.example.edapp.repository.PatientRepository;
import com.example.edapp.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final PatientRepository patientRepository;
    private final ChatGPTApiClient chatGPTApiClient;

    public PrescriptionService(PrescriptionRepository prescriptionRepository, PatientRepository patientRepository, ChatGPTApiClient chatGPTApiClient) {
        this.prescriptionRepository = prescriptionRepository;
        this.patientRepository = patientRepository;
        this.chatGPTApiClient = chatGPTApiClient;
    }

    public Prescription createPrescription(Long patientId, String description) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));
        Prescription prescription = new Prescription(patient, description);
        return prescriptionRepository.save(prescription);
    }

    public List<Prescription> getPrescriptionsByPatient(Long patientId) {
        return prescriptionRepository.findByPatientId(patientId);
    }

    public Prescription generateAndSavePrescription(Long patientId, String diagnosis) {
        String prescriptionText = chatGPTApiClient.generateResponse(
                "Generate a short, concise prescription for the following details:\n"
                        + "Diagnosis: " + diagnosis + "\nPatient ID: " + patientId
        );
        return createPrescription(patientId, prescriptionText);
    }

    public String generatePrescription(Long patientId, String diagnosis) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));

        String prompt = "Generate a prescription for a patient with the following details:\n"
                + "Name: " + patient.getName() + "\n"
                + "Age: " + patient.getAge() + "\n"
                + "Diagnosis: " + diagnosis;

        return chatGPTApiClient.generateResponse(prompt);
    }
}
