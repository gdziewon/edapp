package com.example.edapp.service;

import com.example.edapp.model.Doctor;
import com.example.edapp.model.Patient;
import com.example.edapp.model.VitalSign;
import com.example.edapp.repository.DoctorRepository;
import com.example.edapp.repository.PatientRepository;
import com.example.edapp.repository.VitalSignRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QueueServiceTest {

    @Test
    void testAddAndGetQueue() {
        QueueService queueService = new QueueService();

        Patient patient1 = new Patient("John Doe", 25, "fracture", 3, null);
        Patient patient2 = new Patient("Jane Smith", 30, "critical heart issue", 1, null);

        queueService.addPatientToQueue(1L, patient1);
        queueService.addPatientToQueue(1L, patient2);

        PriorityQueue<Patient> queue = queueService.getQueueForDoctor(1L);
        assertEquals(2, queue.size());
        assertEquals(patient2, queue.poll()); // Highest priority patient first
    }
}
