package pe.edu.upc.center.agecare.users.domain.services;

import pe.edu.upc.center.agecare.users.domain.model.aggregates.Doctor;

public interface DoctorCommandService {
    Doctor assignShift(Long doctorId, String day, String startTime, String endTime);
}
