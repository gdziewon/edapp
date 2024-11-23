package com.example.edapp.service;

import com.example.edapp.model.Doctor;
import com.example.edapp.model.Patient;
import com.example.edapp.repository.DoctorRepository;
import com.example.edapp.repository.PatientRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RegistrationServiceTest {
    @Test
    void testRegisterPatient() {
        // Mock the repositories
        DoctorRepository doctorRepository = mock(DoctorRepository.class);
        PatientRepository patientRepository = mock(PatientRepository.class);
        QueueService queueService = mock(QueueService.class);

        // Initialize the service
        RegistrationService registrationService = new RegistrationService(patientRepository, doctorRepository, queueService);

        // Mock doctor and repository behavior
        Doctor doctor = new Doctor(1L, "Dr. Strange", "Neurology");
        when(doctorRepository.findAll()).thenReturn(List.of(doctor));

        Patient mockSavedPatient = new Patient("John Doe", 35, "critical brain injury", 1, doctor.getId());
        when(patientRepository.save(any(Patient.class))).thenReturn(mockSavedPatient);

        // Test the registration
        Patient patient = registrationService.registerPatient("John Doe", 35, "critical brain injury", null);

        // Verify expected behavior
        assertEquals(1, patient.getTriagePriority());
        assertEquals(1L, patient.getDoctorAssignedId());
        verify(patientRepository, times(1)).save(any(Patient.class));
    }


}
