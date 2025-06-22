package pe.edu.upc.center.agecare.users.interfaces.rest.transform;

import pe.edu.upc.center.agecare.users.domain.model.aggregates.Doctor;
import pe.edu.upc.center.agecare.users.interfaces.rest.resources.CreateDoctorResource;
import pe.edu.upc.center.agecare.users.interfaces.rest.resources.DoctorResource;
import pe.edu.upc.center.agecare.users.interfaces.rest.resources.UpdateDoctorResource;

public class DoctorResourceAssembler {

    public static DoctorResource toResource(Doctor doctor) {
        return new DoctorResource(
                doctor.getId(),
                doctor.getLicenseNumber(),
                doctor.getSpecialty(),
                doctor.getSchedule(),
                doctor.getFullName(),
                doctor.getContactInfo()
        );
    }

    public static Doctor toEntity(CreateDoctorResource resource) {
        return new Doctor(
                null,
                resource.getLicenseNumber(),
                resource.getSpecialty(),
                resource.getSchedule(),
                resource.getFullName(),
                resource.getContactInfo()
        );
    }

    public static Doctor toEntity(UpdateDoctorResource resource, Doctor existing) {
        existing.setSpecialty(resource.getSpecialty());
        existing.setSchedule(resource.getSchedule());
        existing.setFullName(resource.getFullName());
        existing.setContactInfo(resource.getContactInfo());
        return existing;
    }
}
