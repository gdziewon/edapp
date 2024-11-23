package com.example.edapp.service;

import com.example.edapp.model.Doctor;
import com.example.edapp.model.Schedule;
import com.example.edapp.repository.DoctorRepository;
import com.example.edapp.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final DoctorRepository doctorRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, DoctorRepository doctorRepository) {
        this.scheduleRepository = scheduleRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<Schedule> getSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Doctor> getDoctorsForShift(Instant time) {
        return scheduleRepository.findAll().stream()
                .filter(schedule -> !time.isBefore(schedule.getShiftStart()) && !time.isAfter(schedule.getShiftEnd()))
                .map(schedule -> doctorRepository.findById(schedule.getId()).orElse(null))
                .collect(Collectors.toList());
    }
}
