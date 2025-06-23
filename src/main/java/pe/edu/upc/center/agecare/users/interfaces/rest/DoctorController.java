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
import pe.edu.upc.center.agecare.users.interfaces.rest.resources.*;
import pe.edu.upc.center.agecare.users.interfaces.rest.transform.DoctorResourceAssembler;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
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
        return ResponseEntity.status(201).body(DoctorResourceAssembler.toResource(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResource> updateDoctor(@PathVariable Long id, @RequestBody UpdateDoctorResource resource) {
        Doctor updatedDoctor = doctorCommandService.updateDoctor(id, resource);
        return ResponseEntity.ok(DoctorResourceAssembler.toResource(updatedDoctor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorCommandService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/schedule")
    public ResponseEntity<DoctorResource> assignSchedule(
            @PathVariable Long id,
            @RequestBody CreateScheduleResource scheduleResource) {
        var updatedDoctor = doctorCommandService.assignShift(
                id,
                scheduleResource.day(),
                scheduleResource.startTime(),
                scheduleResource.endTime(),
                scheduleResource.appointmentId()
        );
        return ResponseEntity.ok(DoctorResourceAssembler.toResource(updatedDoctor));
    }

    @PutMapping("/{id}/schedules/{scheduleId}")
    public ResponseEntity<DoctorResource> updateSchedule(
            @PathVariable Long id,
            @PathVariable Long scheduleId,
            @RequestBody UpdateScheduleResource scheduleResource) {
        Doctor updatedDoctor = doctorCommandService.updateSchedule(id, scheduleId, scheduleResource);
        return ResponseEntity.ok(DoctorResourceAssembler.toResource(updatedDoctor));
    }


    @DeleteMapping("/{id}/schedules/{scheduleId}")
    public ResponseEntity<DoctorResource> deleteSchedule(
            @PathVariable Long id,
            @PathVariable Long scheduleId) {
        Doctor updatedDoctor = doctorCommandService.deleteSchedule(id, scheduleId);
        return ResponseEntity.ok(DoctorResourceAssembler.toResource(updatedDoctor));
    }

    @PostMapping("/{id}/contactInfo/address")
    public ResponseEntity<DoctorResource> addAddress(
            @PathVariable Long id,
            @RequestBody AddressResource addressResource) {
        Doctor updatedDoctor = doctorCommandService.addAddress(id, addressResource);
        return ResponseEntity.ok(DoctorResourceAssembler.toResource(updatedDoctor));
    }

    @PutMapping("/{id}/contactInfo/address")
    public ResponseEntity<DoctorResource> updateAddress(
            @PathVariable Long id,
            @RequestBody AddressResource addressResource) {
        Doctor updatedDoctor = doctorCommandService.updateAddress(id, addressResource);
        return ResponseEntity.ok(DoctorResourceAssembler.toResource(updatedDoctor));
    }
}
