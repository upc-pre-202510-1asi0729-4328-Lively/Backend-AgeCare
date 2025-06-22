package pe.edu.upc.center.agecare.users.application.internal.commandservices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.agecare.users.domain.model.aggregates.Doctor;
import pe.edu.upc.center.agecare.users.domain.model.valueobjects.Schedule;
import pe.edu.upc.center.agecare.users.domain.services.DoctorCommandService;
import pe.edu.upc.center.agecare.users.infrastructure.persistence.jpa.repositories.DoctorRepository;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class DoctorCommandServiceImpl implements DoctorCommandService {

    private final DoctorRepository doctorRepository;

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
