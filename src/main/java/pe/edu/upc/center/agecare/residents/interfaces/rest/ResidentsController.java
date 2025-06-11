package pe.edu.upc.center.agecare.residents.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.agecare.residents.domain.model.commands.DeleteResidentCommand;
import pe.edu.upc.center.agecare.residents.domain.model.queries.GetAllResidentsQuery;
import pe.edu.upc.center.agecare.residents.domain.model.queries.GetResidentByDniQuery;
import pe.edu.upc.center.agecare.residents.domain.model.queries.GetResidentByIdQuery;
import pe.edu.upc.center.agecare.residents.domain.services.ResidentCommandService;
import pe.edu.upc.center.agecare.residents.domain.services.ResidentQueryService;
import pe.edu.upc.center.agecare.residents.interfaces.rest.resources.CreateResidentResource;
import pe.edu.upc.center.agecare.residents.interfaces.rest.resources.ResidentResource;
import pe.edu.upc.center.agecare.residents.interfaces.rest.transform.CreateResidentCommandFromResourceAssembler;
import pe.edu.upc.center.agecare.residents.interfaces.rest.transform.ResidentResourceFromEntityAssembler;
import pe.edu.upc.center.agecare.residents.interfaces.rest.transform.UpdateResidentCommandFromResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/residents", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Residents", description = "Resident Management Endpoints")
public class ResidentsController {

    private final ResidentQueryService residentQueryService;
    private final ResidentCommandService residentCommandService;

    public ResidentsController(ResidentQueryService residentQueryService, ResidentCommandService residentCommandService) {
        this.residentQueryService = residentQueryService;
        this.residentCommandService = residentCommandService;
    }

    @PostMapping
    public ResponseEntity<ResidentResource> createResident(@RequestBody CreateResidentResource resource) {
        var createCommand = CreateResidentCommandFromResourceAssembler.toCommandFromResource(resource);
        var residentId = residentCommandService.handle(createCommand);

        if (residentId == null || residentId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getResidentByIdQuery = new GetResidentByIdQuery(residentId);
        var optionalResident = residentQueryService.handle(getResidentByIdQuery);

        if (optionalResident.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var residentResource = ResidentResourceFromEntityAssembler.toResourceFromEntity(optionalResident.get());
        return new ResponseEntity<>(residentResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ResidentResource>> getAllResidents() {
        var query = new GetAllResidentsQuery();
        var residents = residentQueryService.handle(query);
        var resources = residents.stream()
                .map(ResidentResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{residentId}")
    public ResponseEntity<ResidentResource> getResidentById(@PathVariable Long residentId) {
        var query = new GetResidentByIdQuery(residentId);
        var optionalResident = residentQueryService.handle(query);

        if (optionalResident.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var resource = ResidentResourceFromEntityAssembler.toResourceFromEntity(optionalResident.get());
        return ResponseEntity.ok(resource);
    }

    @PutMapping("/{residentId}")
    public ResponseEntity<ResidentResource> updateResident(@PathVariable Long residentId, @RequestBody ResidentResource resource) {
        var updateCommand = UpdateResidentCommandFromResourceAssembler.toCommandFromResource(residentId, resource);
        var optionalResident = residentCommandService.handle(updateCommand);

        if (optionalResident.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var updatedResource = ResidentResourceFromEntityAssembler.toResourceFromEntity(optionalResident.get());
        return ResponseEntity.ok(updatedResource);
    }

    @DeleteMapping("/{residentId}")
    public ResponseEntity<?> deleteResident(@PathVariable Long residentId) {
        var deleteCommand = new DeleteResidentCommand(residentId);
        residentCommandService.handle(deleteCommand);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/searchByDni")
    public ResponseEntity<ResidentResource> getResidentByDni(@RequestParam String dni) {
        var query = new GetResidentByDniQuery(dni);
        var optionalResident = residentQueryService.handle(query);

        if (optionalResident.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resource = ResidentResourceFromEntityAssembler.toResourceFromEntity(optionalResident.get());
        return ResponseEntity.ok(resource);
    }

}