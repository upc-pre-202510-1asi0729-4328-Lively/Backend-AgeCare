package pe.edu.upc.center.agecare.users.interfaces.rest.resources;

import lombok.Data;

@Data
public class FamilyMemberResource {
    private Long id;
    private String relationship;
    private Long linkedResidentId;
    private String fullName;      // Lo exponemos como String
    private String contactEmail;  // Lo exponemos como String (contactInfo.email)
}
