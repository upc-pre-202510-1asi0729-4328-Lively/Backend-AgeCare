package pe.edu.upc.center.agecare.users.domain.services;

import pe.edu.upc.center.agecare.users.domain.model.aggregates.Doctor;
import pe.edu.upc.center.agecare.users.interfaces.rest.resources.UpdateDoctorResource;

public interface DoctorCommandService {
    Doctor createDoctor(Doctor doctor);


    Doctor updateDoctor(Long id, UpdateDoctorResource resource);

    void deleteDoctor(Long id);

    Doctor assignShift(Long doctorId, String day, String startTime, String endTime);
}
