package pe.edu.upc.center.agecare.users.application.internal.commandservices;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.agecare.users.domain.model.aggregates.Doctor;
import pe.edu.upc.center.agecare.users.domain.model.valueobjects.Address;
import pe.edu.upc.center.agecare.users.domain.model.valueobjects.ContactInfo;
import pe.edu.upc.center.agecare.users.domain.model.valueobjects.Schedule;
import pe.edu.upc.center.agecare.users.domain.services.DoctorCommandService;
import pe.edu.upc.center.agecare.users.infrastructure.persistence.jpa.repositories.DoctorRepository;
import pe.edu.upc.center.agecare.users.interfaces.rest.resources.AddressResource;
import pe.edu.upc.center.agecare.users.interfaces.rest.resources.ScheduleResource;
import pe.edu.upc.center.agecare.users.interfaces.rest.resources.UpdateDoctorResource;
import pe.edu.upc.center.agecare.users.interfaces.rest.resources.UpdateScheduleResource;
import pe.edu.upc.center.agecare.users.interfaces.rest.transform.DoctorResourceAssembler;

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
    public Doctor assignShift(Long doctorId, String day, String startTime, String endTime, Long appointmentId) {
        // Lógica simplificada o que llame a addSchedule internamente
        ScheduleResource resource = new ScheduleResource(null, day, startTime, endTime, appointmentId);
        return addSchedule(doctorId, resource);
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
    public Doctor addSchedule(Long doctorId, ScheduleResource resource) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id " + doctorId));
        Schedule schedule = new Schedule(null, resource.day(), resource.startTime(), resource.endTime(), resource.appointmentId());
        doctor.getSchedules().add(schedule);
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor updateSchedule(Long doctorId, Long scheduleId, UpdateScheduleResource resource) {
        return doctorRepository.findById(doctorId).map(doctor -> {
            var schedule = doctor.getSchedules().stream()
                    .filter(s -> s.getId().equals(scheduleId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Schedule not found"));
            schedule.setDay(resource.day());
            schedule.setStartTime(resource.startTime());
            schedule.setEndTime(resource.endTime());
            schedule.setAppointmentId(resource.appointmentId());
            return doctorRepository.save(doctor);
        }).orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
    }


    @Override
    public Doctor deleteSchedule(Long doctorId, Long scheduleId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id " + doctorId));

        doctor.getSchedules().removeIf(schedule -> schedule.getId().equals(scheduleId));

        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor addAddress(Long doctorId, AddressResource addressResource) {
        return doctorRepository.findById(doctorId).map(doctor -> {
            var contactInfo = doctor.getContactInfo();
            if (contactInfo == null) {
                contactInfo = new ContactInfo();
            }
            var address = new Address(
                    addressResource.street(),
                    addressResource.city(),
                    addressResource.state(),
                    addressResource.zipCode(),
                    addressResource.country()
            );
            contactInfo.setAddress(address);
            doctor.setContactInfo(contactInfo);
            return doctorRepository.save(doctor);
        }).orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
    }

    @Override
    public Doctor updateAddress(Long doctorId, AddressResource addressResource) {
        return doctorRepository.findById(doctorId).map(doctor -> {
            var contactInfo = doctor.getContactInfo();
            if (contactInfo == null || contactInfo.getAddress() == null) {
                throw new IllegalArgumentException("Address not found for update");
            }
            var address = contactInfo.getAddress();
            address.setStreet(addressResource.street());
            address.setCity(addressResource.city());
            address.setState(addressResource.state());
            address.setZipCode(addressResource.zipCode());
            address.setCountry(addressResource.country());
            return doctorRepository.save(doctor);
        }).orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
    }

}
