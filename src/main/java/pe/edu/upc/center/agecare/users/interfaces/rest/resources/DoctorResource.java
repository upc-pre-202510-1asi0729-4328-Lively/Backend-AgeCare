package pe.edu.upc.center.agecare.users.interfaces.rest.resources;

import pe.edu.upc.center.agecare.users.domain.model.valueobjects.ContactInfo;
import pe.edu.upc.center.agecare.users.domain.model.valueobjects.FullName;
import pe.edu.upc.center.agecare.users.domain.model.valueobjects.Schedule;

import java.util.List;

public record DoctorResource(
        Long id,
        String licenseNumber,
        String specialty,
        List<Schedule> schedules,
        FullName fullName,
        ContactInfo contactInfo
) {}
