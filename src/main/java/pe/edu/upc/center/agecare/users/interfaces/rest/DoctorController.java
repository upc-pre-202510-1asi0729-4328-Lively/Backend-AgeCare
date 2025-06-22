package pe.edu.upc.center.agecare.users.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.agecare.users.domain.model.aggregates.Doctor;
import pe.edu.upc.center.agecare.users.domain.services.DoctorCommandService;
import pe.edu.upc.center.agecare.users.domain.services.DoctorQueryService;
import pe.edu.upc.center.agecare.users.interfaces.rest.resources.CreateDoctorResource;
import pe.edu.upc.center.agecare.users.interfaces.rest.resources.DoctorResource;
import pe.edu.upc.center.agecare.users.interfaces.rest.resources.UpdateDoctorResource;
import pe.edu.upc.center.agecare.users.interfaces.rest.transform.DoctorResourceAssembler;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
@Tag(name = "Doctors", description = "Operations related to Doctors")

public class DoctorController {

    private final DoctorCommandService doctorCommandService;
    private final DoctorQueryService doctorQueryService;

    public DoctorController(DoctorCommandService doctorCommandService, DoctorQueryService doctorQueryService) {
        this.doctorCommandService = doctorCommandService;
        this.doctorQueryService = doctorQueryService;
    }

    @GetMapping
    public List<DoctorResource> getAllDoctors() {
        return doctorQueryService.getAllDoctors().stream()
                .map(DoctorResourceAssembler::toResource)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResource> getDoctorById(@PathVariable Long id) {
        return doctorQueryService.getDoctorById(id)
                .map(DoctorResourceAssembler::toResource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DoctorResource> createDoctor(@RequestBody CreateDoctorResource resource) {
        Doctor doctor = DoctorResourceAssembler.toEntity(resource);
        Doctor saved = doctorCommandService.createDoctor(doctor);
        return ResponseEntity.ok(DoctorResourceAssembler.toResource(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResource> updateDoctor(@PathVariable Long id, @RequestBody UpdateDoctorResource resource) {
        Doctor updated = doctorCommandService.updateDoctor(id, resource);
        return ResponseEntity.ok(DoctorResourceAssembler.toResource(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorCommandService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
