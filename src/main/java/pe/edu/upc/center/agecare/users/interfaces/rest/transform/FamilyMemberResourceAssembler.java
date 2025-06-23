package pe.edu.upc.center.agecare.users.interfaces.rest.transform;

import pe.edu.upc.center.agecare.users.domain.model.aggregates.FamilyMember;
import pe.edu.upc.center.agecare.users.interfaces.rest.resources.CreateFamilyMemberResource;
import pe.edu.upc.center.agecare.users.interfaces.rest.resources.UpdateFamilyMemberResource;
import pe.edu.upc.center.agecare.users.interfaces.rest.resources.FamilyMemberResource;

public class FamilyMemberResourceAssembler {

    public static FamilyMemberResource toResource(FamilyMember familyMember) {
        return new FamilyMemberResource(
                familyMember.getId(),
                familyMember.getRelationship(),
                familyMember.getLinkedResidentId(),
                familyMember.getFullName(),
                familyMember.getContactInfo()
        );
    }

    public static FamilyMember toEntity(CreateFamilyMemberResource resource) {
        FamilyMember familyMember = new FamilyMember();
        familyMember.setRelationship(resource.relationship());
        familyMember.setLinkedResidentId(resource.linkedResidentId());
        familyMember.setFullName(resource.fullName());
        //familyMember.setContactInfo(resource.contactInfo());
        return familyMember;
    }

    public static FamilyMember toEntity(UpdateFamilyMemberResource resource) {
        FamilyMember familyMember = new FamilyMember();
        familyMember.setRelationship(resource.relationship());
        familyMember.setLinkedResidentId(resource.linkedResidentId());
        familyMember.setFullName(resource.fullName());
        //familyMember.setContactInfo(resource.contactInfo());
        return familyMember;
    }
}
