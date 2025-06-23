package pe.edu.upc.center.agecare.users.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.agecare.users.domain.model.aggregates.FamilyMember;
import pe.edu.upc.center.agecare.users.domain.services.FamilyMemberCommandService;
import pe.edu.upc.center.agecare.users.domain.services.FamilyMemberQueryService;
import pe.edu.upc.center.agecare.users.interfaces.rest.resources.CreateFamilyMemberResource;
import pe.edu.upc.center.agecare.users.interfaces.rest.resources.FamilyMemberResource;
import pe.edu.upc.center.agecare.users.interfaces.rest.resources.UpdateFamilyMemberResource;
import pe.edu.upc.center.agecare.users.interfaces.rest.transform.FamilyMemberResourceAssembler;

import java.util.List;

@RestController
@RequestMapping("/api/v1/family-members")
@Tag(name = "FamilyMembers", description = "Operations related to FamilyMembers")

public class FamilyMemberController {

    private final FamilyMemberCommandService familyMemberCommandService;
    private final FamilyMemberQueryService familyMemberQueryService;

    public FamilyMemberController(FamilyMemberCommandService familyMemberCommandService,
                                  FamilyMemberQueryService familyMemberQueryService) {
        this.familyMemberCommandService = familyMemberCommandService;
        this.familyMemberQueryService = familyMemberQueryService;
    }

    @PostMapping
    public ResponseEntity<FamilyMemberResource> createFamilyMember(@RequestBody CreateFamilyMemberResource resource) {
        var familyMember = familyMemberCommandService.createFamilyMember(
                FamilyMemberResourceAssembler.toEntity(resource)
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(FamilyMemberResourceAssembler.toResource(familyMember));
    }

    @GetMapping
    public ResponseEntity<List<FamilyMember>> getAllFamilyMembers() {
        return ResponseEntity.ok(familyMemberQueryService.getAllFamilyMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FamilyMember> getFamilyMemberById(@PathVariable Long id) {
        return familyMemberQueryService.getFamilyMemberById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FamilyMemberResource> updateFamilyMember(
            @PathVariable Long id,
            @RequestBody UpdateFamilyMemberResource resource) {

        FamilyMember updatedFamilyMember = familyMemberCommandService.updateFamilyMember(
                id, FamilyMemberResourceAssembler.toEntity(resource));

        return ResponseEntity.ok(FamilyMemberResourceAssembler.toResource(updatedFamilyMember));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFamilyMember(@PathVariable Long id) {
        familyMemberCommandService.deleteFamilyMember(id);
        return ResponseEntity.noContent().build();
    }
}
