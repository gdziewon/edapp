package com.example.edapp.controller;

import com.example.edapp.model.Patient;
import com.example.edapp.service.QueueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

@RestController
@RequestMapping("/queues")
public class QueueController {
    private final QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<List<Patient>> getQueue(@PathVariable Long doctorId) {
        PriorityQueue<Patient> queue = queueService.getQueueForDoctor(doctorId);
        return ResponseEntity.ok(new ArrayList<>(queue));
    }
}

