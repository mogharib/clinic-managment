package com.task.patient_appointment.dataAccessServices;

import com.task.patient_appointment.Dao.PatientDao;
import com.task.patient_appointment.repos.PatientRepo;
import org.springframework.stereotype.Repository;

@Repository
public class PatientDataAccessService implements PatientDao {
    private final PatientRepo patientRepo;

    public PatientDataAccessService(PatientRepo patientRepo) {
        this.patientRepo = patientRepo;
    }

    @Override
    public boolean existsById(Long patientId) {
        return patientRepo.existsById(patientId);
    }
}
