package com.example.edapp.service;

import com.example.edapp.model.Patient;
import com.example.edapp.model.VitalSign;
import com.example.edapp.repository.PatientRepository;
import com.example.edapp.repository.VitalSignRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class MonitoringServiceTest {
    @Test
    void testRecordAndRetrieveVitalSigns() {
        Patient patient = new Patient("John Doe", 30, "critical", 1, 1L);
        patient.setId(1L);

        VitalSignRepository vitalSignRepository = mock(VitalSignRepository.class);
        PatientRepository patientRepository = mock(PatientRepository.class);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        MonitoringService monitoringService = new MonitoringService(vitalSignRepository, patientRepository);
        monitoringService.recordVitalSigns(1L, "120/80", 80, 98);

        verify(vitalSignRepository, times(1)).save(any(VitalSign.class));
    }

}
