package pe.edu.upc.center.agecare.users.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Retrieve all doctors",
            description = "Get a list of all doctors in the system",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Doctors retrieved successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DoctorResource.class)))
            }
    )
    @GetMapping
    public List<DoctorResource> getAllDoctors() {
        return doctorQueryService.getAllDoctors().stream()
                .map(DoctorResourceAssembler::toResource)
                .toList();
    }

    @Operation(
            summary = "Retrieve doctor by ID",
            description = "Get details of a specific doctor by their ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Doctor found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DoctorResource.class))),
                    @ApiResponse(responseCode = "404", description = "Doctor not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<DoctorResource> getDoctorById(@PathVariable Long id) {
        return doctorQueryService.getDoctorById(id)
                .map(DoctorResourceAssembler::toResource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create a new doctor",
            description = "Register a new doctor in the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Doctor created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DoctorResource.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    @PostMapping
    public ResponseEntity<DoctorResource> createDoctor(@RequestBody CreateDoctorResource resource) {
        Doctor doctor = DoctorResourceAssembler.toEntity(resource);
        Doctor saved = doctorCommandService.createDoctor(doctor);
        return ResponseEntity.status(201).body(DoctorResourceAssembler.toResource(saved));
    }

    @Operation(
            summary = "Update an existing doctor",
            description = "Update the information of an existing doctor",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Doctor updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DoctorResource.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Doctor not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<DoctorResource> updateDoctor(@PathVariable Long id, @RequestBody UpdateDoctorResource resource) {
        Doctor updated = doctorCommandService.updateDoctor(id, resource);
        return ResponseEntity.ok(DoctorResourceAssembler.toResource(updated));
    }

    @Operation(
            summary = "Delete a doctor",
            description = "Remove a doctor from the system by their ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Doctor deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Doctor not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorCommandService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
