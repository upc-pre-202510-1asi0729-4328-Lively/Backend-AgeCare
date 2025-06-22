package pe.edu.upc.center.agecare.residents.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.agecare.residents.domain.model.aggregates.Resident;
import pe.edu.upc.center.agecare.residents.domain.model.entities.MedicalHistory;
import pe.edu.upc.center.agecare.residents.infrastructure.persistence.jpa.repositories.ResidentRepository;
import pe.edu.upc.center.agecare.residents.interfaces.rest.resources.MedicalHistoryResource;

import java.util.List;

@RestController
@RequestMapping("/api/v1/residents/{residentId}/medical-histories")
@Tag(name = "Medical Histories", description = "Resident Medical History Management Endpoints")
public class MedicalHistoryController {

    private final ResidentRepository residentRepository;

    public MedicalHistoryController(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    @GetMapping
    public ResponseEntity<List<MedicalHistory>> getMedicalHistories(@PathVariable Long residentId) {
        return residentRepository.findById(residentId)
                .map(resident -> ResponseEntity.ok(resident.getMedicalHistories()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<List<MedicalHistory>> addMedicalHistory(
            @PathVariable Long residentId,
            @RequestBody MedicalHistoryResource resource) {

        return residentRepository.findById(residentId)
                .map(resident -> {
                    MedicalHistory medicalHistory = new MedicalHistory(
                            new java.util.Date(),  // Aquí podrías cambiar por un campo si lo quieres recibir del recurso
                            resource.diagnosis(),
                            resource.treatment()
                    );
                    resident.addMedicalHistory(medicalHistory);
                    residentRepository.save(resident);
                    return ResponseEntity.status(HttpStatus.CREATED).body(resident.getMedicalHistories());
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{medicalHistoryId}")
    public ResponseEntity<?> removeMedicalHistory(
            @PathVariable Long residentId,
            @PathVariable Long medicalHistoryId) {

        return residentRepository.findById(residentId)
                .map(resident -> {
                    resident.removeMedicalHistoryById(medicalHistoryId);
                    residentRepository.save(resident);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
