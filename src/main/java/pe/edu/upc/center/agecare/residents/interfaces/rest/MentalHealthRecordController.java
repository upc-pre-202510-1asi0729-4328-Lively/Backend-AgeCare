package pe.edu.upc.center.agecare.residents.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.agecare.residents.domain.model.entities.MentalHealthRecord;
import pe.edu.upc.center.agecare.residents.infrastructure.persistence.jpa.repositories.ResidentRepository;
import pe.edu.upc.center.agecare.residents.interfaces.rest.resources.MentalHealthRecordResource;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/residents/{residentId}/mental-health-records")
@Tag(name = "Mental Health Records", description = "Resident Mental Health Record Management Endpoints")
public class MentalHealthRecordController {

    private final ResidentRepository residentRepository;

    public MentalHealthRecordController(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    @GetMapping
    public ResponseEntity<List<MentalHealthRecord>> getMentalHealthRecords(@PathVariable Long residentId) {
        return residentRepository.findById(residentId)
                .map(resident -> ResponseEntity.ok(resident.getMentalHealthRecords()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<List<MentalHealthRecord>> addMentalHealthRecord(
            @PathVariable Long residentId,
            @RequestBody MentalHealthRecordResource resource) {

        return residentRepository.findById(residentId)
                .map(resident -> {
                    MentalHealthRecord record = new MentalHealthRecord(
                            new Date(),  // Puedes cambiar a un campo del resource si quieres recibir la fecha
                            resource.diagnosis(),
                            resource.treatment()
                    );
                    resident.addMentalHealthRecord(record);
                    residentRepository.save(resident);
                    return ResponseEntity.status(HttpStatus.CREATED).body(resident.getMentalHealthRecords());
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{mentalHealthRecordId}")
    public ResponseEntity<?> removeMentalHealthRecord(
            @PathVariable Long residentId,
            @PathVariable Long mentalHealthRecordId) {

        return residentRepository.findById(residentId)
                .map(resident -> {
                    resident.getMentalHealthRecords().removeIf(record -> record.getId().equals(mentalHealthRecordId));
                    residentRepository.save(resident);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
