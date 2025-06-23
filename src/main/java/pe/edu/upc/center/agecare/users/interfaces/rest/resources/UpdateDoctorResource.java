package pe.edu.upc.center.agecare.users.interfaces.rest.resources;

import pe.edu.upc.center.agecare.users.domain.model.valueobjects.FullName;
import pe.edu.upc.center.agecare.users.domain.model.valueobjects.ContactInfo;

public record UpdateDoctorResource(
        String licenseNumber,
        String specialty,
        //UpdateScheduleResource schedule,
        FullName fullName,
        ContactInfo contactInfo
) {}

