package com.task.patient_appointment.dataAccessServices;

import com.task.patient_appointment.Dao.AppointmentDao;
import com.task.patient_appointment.models.Appointment;
import com.task.patient_appointment.repos.AppointmentRepo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppointmentDataAccessService implements AppointmentDao {
    private final AppointmentRepo appointmentRepo;

    public AppointmentDataAccessService(AppointmentRepo appointmentRepo) {
        this.appointmentRepo = appointmentRepo;
    }

    @Override
    public List<Appointment> findAllByAppointmentDateBetween(long startDate, long endDate) {
        return appointmentRepo.findAllByAppointmentStartDateBetween(startDate, endDate);
    }

    @Override
    public Appointment addAppointment(Appointment appointment) {
        return appointmentRepo.save(appointment);
    }

    @Override
    public void cancelAppointment(Appointment appointment) {
        appointmentRepo.save(appointment);
    }

    @Override
    public List<Appointment> findByAppointmentDateBefore(Long today) {
        return appointmentRepo.findAllByAppointmentStartDateBefore(today);
    }

    @Override
    public List<Appointment> findByAppointmentDateAfter(Long today) {
        return appointmentRepo.findAllByAppointmentStartDateAfter(today);
    }

    @Override
    public List<Appointment> findAllByPatientId(Long patientId) {
        return appointmentRepo.findAllByPatient_Id(patientId);
    }

    @Override
    public List<Appointment> findAllByPatientNameContaining(String patientName) {
        return appointmentRepo.findAllByPatient_NameContaining(patientName);
    }

    @Override
    public boolean existsById(Long appointmentId) {
        return appointmentRepo.existsById(appointmentId);
    }

    @Override
    public Appointment getAppointmentById(Long appointmentId) {
        return appointmentRepo.findById(appointmentId).get();
    }
}
