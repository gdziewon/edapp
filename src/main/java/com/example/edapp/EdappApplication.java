package com.example.edapp;

import com.example.edapp.model.Doctor;
import com.example.edapp.model.Patient;
import com.example.edapp.repository.DoctorRepository;
import com.example.edapp.repository.PatientRepository;
import com.example.edapp.service.QueueService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class EdappApplication implements CommandLineRunner {
    private final DoctorRepository doctorRepository;
    private final QueueService queueService;
    private final Logger log = Logger.getLogger(EdappApplication.class.getName());

    public EdappApplication(DoctorRepository doctorRepository, QueueService queueService) {
        this.doctorRepository = doctorRepository;
        this.queueService = queueService;
    }

    public static void main(String[] args) {
        SpringApplication.run(EdappApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Doctor doctor1 = doctorRepository.save(new Doctor("Dr. House", "Diagnostics"));
        Doctor doctor2 = doctorRepository.save(new Doctor("Dr. Strange", "Neurology"));
        Doctor generalDoctor = doctorRepository.save(new Doctor("Dr. Smith", "General Medicine"));

        doctorRepository.flush();

        log.info("Doctors initialized: " + doctor1.getName() + ", " + doctor2.getName() + ", " + generalDoctor.getName());

        queueService.initializeQueueForDoctor(doctor1.getId());
        queueService.initializeQueueForDoctor(doctor2.getId());
        queueService.initializeQueueForDoctor(generalDoctor.getId());
    }

}