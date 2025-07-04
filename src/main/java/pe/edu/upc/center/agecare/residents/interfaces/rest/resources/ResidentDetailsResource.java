// ResidentDetailsResource.java
package pe.edu.upc.center.agecare.residents.interfaces.rest.resources;

import pe.edu.upc.center.agecare.residents.domain.model.entities.Medication;
import pe.edu.upc.center.agecare.residents.domain.model.entities.MedicalHistory;
import pe.edu.upc.center.agecare.residents.domain.model.entities.MentalHealthRecord;

import java.util.List;

public record ResidentDetailsResource(
        Long id,
        String fullName,
        String dni,
        List<Medication> medications,
        List<MedicalHistory> medicalHistories,
        List<MentalHealthRecord> mentalHealthRecords
) {}

