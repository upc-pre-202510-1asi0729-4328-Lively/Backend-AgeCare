package pe.edu.upc.center.agecare.users.application.internal.commandservices;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.agecare.users.domain.model.aggregates.Doctor;
import pe.edu.upc.center.agecare.users.domain.model.valueobjects.Schedule;
import pe.edu.upc.center.agecare.users.domain.services.DoctorCommandService;
import pe.edu.upc.center.agecare.users.infrastructure.persistence.jpa.repositories.DoctorRepository;
import pe.edu.upc.center.agecare.users.interfaces.rest.resources.UpdateDoctorResource;
import pe.edu.upc.center.agecare.users.interfaces.rest.transform.DoctorResourceAssembler;

import java.time.LocalTime;

@Service
public class DoctorCommandServiceImpl implements DoctorCommandService {

    private final DoctorRepository doctorRepository;

    public DoctorCommandServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor updateDoctor(Long id, UpdateDoctorResource resource) {
        Doctor existing = doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id " + id));
        return doctorRepository.save(DoctorResourceAssembler.toEntity(resource, existing));
    }

    @Override
    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
    @Override
    public Doctor assignShift(Long doctorId, String day, String startTime, String endTime) {
        return doctorRepository.findById(doctorId).map(doctor -> {
            Schedule schedule = new Schedule();
            schedule.setDay(day);
            schedule.setStartTime(LocalTime.parse(startTime));
            schedule.setEndTime(LocalTime.parse(endTime));
            doctor.setSchedule(schedule);
            return doctorRepository.save(doctor);
        }).orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
    }

}
