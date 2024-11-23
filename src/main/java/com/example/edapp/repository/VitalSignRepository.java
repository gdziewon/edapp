package com.example.edapp.repository;

import com.example.edapp.model.VitalSign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface VitalSignRepository extends JpaRepository<VitalSign, Long> {
    @Query("SELECT v FROM VitalSign v JOIN FETCH v.patient WHERE v.patient.id = :patientId ORDER BY v.recordedAt DESC")
    List<VitalSign> findByPatientIdOrderByRecordedAtDesc(@Param("patientId") Long patientId);

    List<VitalSign> findByPatientIdAndRecordedAtBetween(Long patientId, Instant start, Instant end);
}
