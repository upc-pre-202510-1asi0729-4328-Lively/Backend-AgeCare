package pe.edu.upc.center.agecare.users.domain.services;

import pe.edu.upc.center.agecare.users.domain.model.aggregates.Doctor;

import java.util.Optional;

public interface DoctorQueryService {
    Optional<Doctor> getDoctorById(Long id);
}
