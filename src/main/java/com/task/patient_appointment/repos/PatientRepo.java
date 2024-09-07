package com.task.patient_appointment.repos;

import com.task.patient_appointment.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepo extends JpaRepository<Patient, Long> {}
