package pe.edu.upc.center.agecare.users.interfaces.rest.resources;

import lombok.Data;

@Data
public class CreateFamilyMemberResource {
    private String relationship;
    private Long linkedResidentId;
    private String fullName;
    private String contactEmail;
}
