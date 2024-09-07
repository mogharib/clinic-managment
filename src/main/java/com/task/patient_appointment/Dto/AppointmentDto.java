package com.task.patient_appointment.Dto;

import com.task.patient_appointment.models.Patient;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {
    @NonNull
    private Patient patient;
    @NonNull
    private Long appointmentStartDate;
    @NonNull
    private Long appointmentEndDate;
    @Size(max = 255)
    private String cancellationReason;
}
