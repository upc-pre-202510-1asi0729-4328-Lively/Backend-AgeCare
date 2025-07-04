package pe.edu.upc.center.agecare.residents.interfaces.rest.transform;

import pe.edu.upc.center.agecare.residents.domain.model.aggregates.Resident;
import pe.edu.upc.center.agecare.residents.interfaces.rest.resources.ResidentDetailsResource;

public class ResidentDetailsResourceFromEntityAssembler {

    public static ResidentDetailsResource toResourceFromEntity(Resident resident) {
        return new ResidentDetailsResource(
                resident.getId(),
                resident.getFullNameAsString(), // <-- ahora sí usas el string completo
                resident.getDni(),
                resident.getMedication(),
                resident.getMedicalHistories(),
                resident.getMentalHealthRecords()
        );
    }
}
