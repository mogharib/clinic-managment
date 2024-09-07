package com.task.patient_appointment.services;

import com.task.patient_appointment.Dao.AppointmentDao;
import com.task.patient_appointment.Dao.PatientDao;
import com.task.patient_appointment.Dto.AppointmentDto;
import com.task.patient_appointment.exceptions.InvalidDateException;
import com.task.patient_appointment.exceptions.InvalidSearchCriteriaException;
import com.task.patient_appointment.exceptions.ResourceNotFoundException;
import com.task.patient_appointment.models.Appointment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentService extends BaseService {
    private final AppointmentDao appointmentDao;
    private final PatientDao patientDao;

    public List<AppointmentDto> getAllTodayAppointments() {

        // start of day
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        long startOfDay = todayStart.toInstant(ZoneOffset.UTC).toEpochMilli();

        // end of day
        LocalDateTime todayEnd = LocalDate.now().atTime(LocalTime.MAX);
        long endOfDay = todayEnd.toInstant(ZoneOffset.UTC).toEpochMilli();
        return Arrays.asList(modelMapper.map(appointmentDao.findAllByAppointmentDateBetween(startOfDay, endOfDay),
                AppointmentDto[].class));
    }

    public AppointmentDto addAppointment(Appointment appointment) {

        Long startDate = appointment.getAppointmentStartDate();
        Long endDate = appointment.getAppointmentEndDate();

        if (startDate == null || endDate == null || endDate < startDate) {
            throw new InvalidDateException(" End date must be after start date.");
        }

        boolean availableAppointment = appointmentDao.findAllByAppointmentDateBetween(startDate, endDate)
                .stream()
                .anyMatch(existingAppointment ->
                        (startDate > existingAppointment.getAppointmentEndDate()) && endDate < ((existingAppointment.getAppointmentStartDate()))
                );

        if (availableAppointment) {
            throw new InvalidDateException("appointment already exists");
        }
        return modelMapper.map(appointmentDao.addAppointment(appointment), AppointmentDto.class);
    }

    public void cancelAppointment(Long appointmentId, String cancellationReason) {
        if (!appointmentDao.existsById(appointmentId)) {
            throw new ResourceNotFoundException("Appointment not found.");
        }
        Appointment appointment = appointmentDao.getAppointmentById(appointmentId);
        appointment.setCancellationReason(cancellationReason);
        appointmentDao.cancelAppointment(appointment);
    }

    public List<AppointmentDto> getAllFutureAppointments(long date) {
        return Arrays.asList(modelMapper.map(appointmentDao.findByAppointmentDateAfter(date),
                AppointmentDto[].class));
    }

    public List<AppointmentDto> getAllPreviousAppointments(long date) {
        return Arrays.asList(modelMapper.map(appointmentDao.findByAppointmentDateBefore(date),
                AppointmentDto[].class));
    }

    public List<AppointmentDto> getAllPatientAppointments(Long patientId) {
        if (!patientDao.existsById(patientId)) {
            throw new ResourceNotFoundException("Patient not found.");
        }
        return Arrays.asList(modelMapper.map(appointmentDao.findAllByPatientId(patientId),
                AppointmentDto[].class));
    }

    public List<AppointmentDto> searchByPatientName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidSearchCriteriaException("Name cannot be null or empty.");
        }
        return Arrays.asList(modelMapper.map(appointmentDao.findAllByPatientNameContaining(name),
                AppointmentDto[].class));
    }

}
