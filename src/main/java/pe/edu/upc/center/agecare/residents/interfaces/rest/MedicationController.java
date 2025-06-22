package pe.edu.upc.center.agecare.residents.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.agecare.residents.domain.model.aggregates.Resident;
import pe.edu.upc.center.agecare.residents.domain.model.entities.Medication;
import pe.edu.upc.center.agecare.residents.infrastructure.persistence.jpa.repositories.ResidentRepository;
import pe.edu.upc.center.agecare.residents.interfaces.rest.resources.MedicationResource;

import java.util.List;

@RestController
@RequestMapping("/api/v1/residents/{residentId}/medications")
@Tag(name = "Medications", description = "Medication Management Endpoints")
public class MedicationController {

    private final ResidentRepository residentRepository;

    public MedicationController(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    @PostMapping
    public ResponseEntity<Medication> addMedication(
            @PathVariable Long residentId,
            @RequestBody MedicationResource resource) {

        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new IllegalArgumentException("Resident not found with id: " + residentId));

        Medication medication = new Medication(resource.name(), resource.frequency());
        resident.addMedication(medication);

        residentRepository.save(resident);

        return new ResponseEntity<>(medication, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Medication>> getMedications(@PathVariable Long residentId) {
        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new IllegalArgumentException("Resident not found with id: " + residentId));

        return ResponseEntity.ok(resident.getMedication());
    }

    @DeleteMapping("/{medicationId}")
    public ResponseEntity<?> deleteMedication(
            @PathVariable Long residentId,
            @PathVariable Long medicationId) {

        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new IllegalArgumentException("Resident not found with id: " + residentId));

        Medication medicationToRemove = resident.getMedication().stream()
                .filter(m -> m.getId().equals(medicationId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Medication not found with id: " + medicationId));

        resident.removeMedication(medicationToRemove);
        residentRepository.save(resident);

        return ResponseEntity.noContent().build();
    }
}
