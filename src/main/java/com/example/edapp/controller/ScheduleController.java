package com.example.edapp.controller;

import com.example.edapp.model.Schedule;
import com.example.edapp.service.RegistrationService;
import com.example.edapp.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final RegistrationService registrationService;

    public ScheduleController(ScheduleService scheduleService, RegistrationService registrationService) {
        this.scheduleService = scheduleService;
        this.registrationService = registrationService;
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> getSchedules() {
        return ResponseEntity.ok(scheduleService.getSchedules());
    }

    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) {
        return ResponseEntity.ok(scheduleService.save(schedule));
    }
}


