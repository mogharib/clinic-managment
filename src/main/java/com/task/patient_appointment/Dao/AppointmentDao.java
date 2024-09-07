package com.task.patient_appointment.Dao;

import com.task.patient_appointment.models.Appointment;

import java.util.List;

public interface AppointmentDao {
    List<Appointment> findAllByAppointmentDateBetween(long startDate, long endDate);
    Appointment addAppointment(Appointment appointment);
    void cancelAppointment(Appointment appointment);
    List<Appointment> findByAppointmentDateBefore(Long today);
    List<Appointment> findByAppointmentDateAfter(Long today);

    List<Appointment> findAllByPatientNameContaining(String patientName);
    List<Appointment> findAllByPatientId(Long patientId);
    boolean existsById(Long appointmentId);

    Appointment getAppointmentById(Long appointmentId);
}
