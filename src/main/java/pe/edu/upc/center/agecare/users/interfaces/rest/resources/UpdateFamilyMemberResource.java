package pe.edu.upc.center.agecare.users.interfaces.rest.resources;

import pe.edu.upc.center.agecare.users.domain.model.valueobjects.ContactInfo;
import pe.edu.upc.center.agecare.users.domain.model.valueobjects.FullName;

public record UpdateFamilyMemberResource(
        String relationship,
        Long linkedResidentId,
        FullName fullName
        //ContactInfo contactInfo
) {}
