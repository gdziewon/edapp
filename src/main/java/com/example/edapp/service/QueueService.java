package com.example.edapp.service;

import com.example.edapp.model.Patient;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

@Service
public class QueueService {
    private final Map<Long, PriorityQueue<Patient>> doctorQueues = new HashMap<>();

    public void addPatientToQueue(Long doctorId, Patient patient) {
        doctorQueues.computeIfAbsent(doctorId, id -> new PriorityQueue<>(
                Comparator.comparingInt(Patient::getTriagePriority)
        ));

        if (patient != null) {
            doctorQueues.get(doctorId).add(patient);
        }
    }

    public void initializeQueueForDoctor(Long doctorId) {
        doctorQueues.computeIfAbsent(doctorId, id -> new PriorityQueue<>(
                Comparator.comparingInt(Patient::getTriagePriority)
        ));
    }

    public void updatePatientPriority(Long doctorId, Long patientId, int newPriority) {
        PriorityQueue<Patient> queue = doctorQueues.get(doctorId);
        if (queue != null) {
            Patient patient = queue.stream().filter(p -> p.getId().equals(patientId)).findFirst().orElse(null);
            if (patient != null) {
                queue.remove(patient);
                patient.setTriagePriority(newPriority);
                queue.add(patient);
            } else {
                throw new RuntimeException("Patient not found in queue");
            }
        } else {
            throw new RuntimeException("No queue for doctor ID: " + doctorId);
        }
    }

    public PriorityQueue<Patient> getQueueForDoctor(Long doctorId) {
        return doctorQueues.getOrDefault(doctorId, new PriorityQueue<>());
    }

    public void clearQueues() {
        doctorQueues.clear();
    }

    public Patient peekNextPatient(Long doctorId) {
        PriorityQueue<Patient> queue = doctorQueues.get(doctorId);
        return (queue != null && !queue.isEmpty()) ? queue.peek() : null;
    }

    public Patient removePatientFromQueue(Long doctorId, Long patientId) {
        PriorityQueue<Patient> queue = doctorQueues.get(doctorId);
        if (queue != null) {
            Patient toRemove = queue.stream().filter(p -> p.getId().equals(patientId)).findFirst().orElse(null);
            if (toRemove != null) {
                queue.remove(toRemove);
                return toRemove;
            }
        }
        throw new RuntimeException("Patient not found in queue");
    }
}
