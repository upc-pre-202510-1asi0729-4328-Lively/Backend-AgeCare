package pe.edu.upc.center.agecare.residents.interfaces.rest.transform;

import pe.edu.upc.center.agecare.residents.domain.model.aggregates.Resident;
import pe.edu.upc.center.agecare.residents.interfaces.rest.resources.ResidentResource;

public class ResidentResourceFromEntityAssembler {
    public static ResidentResource toResourceFromEntity(Resident resident) {
        return new ResidentResource(
                resident.getDni(),
                resident.getFullName().firstName(),
                resident.getFullName().lastName(),
                resident.getAddress().city(),
                resident.getAddress().state(),
                resident.getAddress().country(),
                resident.getAddress().street(),
                resident.getAddress().zipCode(),
                resident.getBirthDate(),
                resident.getGender(),
                resident.getReceipt().receiptId()
        );
    }
}
