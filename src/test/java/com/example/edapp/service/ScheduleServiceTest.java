package com.example.edapp.service;

import com.example.edapp.model.Schedule;
import com.example.edapp.repository.DoctorRepository;
import com.example.edapp.repository.ScheduleRepository;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ScheduleServiceTest {
    @Test
    void testGetSchedules() {
        ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
        DoctorRepository doctorRepository = mock(DoctorRepository.class);
        ScheduleService scheduleService = new ScheduleService(scheduleRepository, doctorRepository);

        Schedule schedule = new Schedule(null, "John", "Doctor", Instant.now(), Instant.now().plus(8, ChronoUnit.HOURS));
        when(scheduleRepository.findAll()).thenReturn(List.of(schedule));

        List<Schedule> schedules = scheduleService.getSchedules();

        assertEquals(1, schedules.size());
        assertEquals("John", schedules.get(0).getStaffName());
        verify(scheduleRepository, times(1)).findAll();
    }

}
