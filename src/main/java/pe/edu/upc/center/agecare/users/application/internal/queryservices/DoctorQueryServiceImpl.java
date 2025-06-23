package pe.edu.upc.center.agecare.users.application.internal.queryservices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.agecare.users.domain.model.aggregates.Doctor;
import pe.edu.upc.center.agecare.users.domain.services.DoctorQueryService;
import pe.edu.upc.center.agecare.users.infrastructure.persistence.jpa.repositories.DoctorRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DoctorQueryServiceImpl implements DoctorQueryService {

    private final DoctorRepository doctorRepository;

    public DoctorQueryServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }
}
