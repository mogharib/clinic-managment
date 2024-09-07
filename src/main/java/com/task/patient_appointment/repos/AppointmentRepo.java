package com.task.patient_appointment.repos;

import com.task.patient_appointment.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepo extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByAppointmentStartDateBefore(Long today);

    List<Appointment> findAllByAppointmentStartDateBetween(long startDate, long endDate);

    List<Appointment> findAllByPatient_Id(Long patientId);

    List<Appointment> findAllByPatient_NameContaining(String patientName);

    List<Appointment> findAllByAppointmentStartDateAfter(Long today);
}
