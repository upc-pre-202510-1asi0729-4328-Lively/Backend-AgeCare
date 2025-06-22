package pe.edu.upc.center.agecare.users.interfaces.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.agecare.users.domain.model.aggregates.FamilyMember;
import pe.edu.upc.center.agecare.users.domain.services.FamilyMemberCommandService;
import pe.edu.upc.center.agecare.users.domain.services.FamilyMemberQueryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/family-members")
public class FamilyMemberController {

    private final FamilyMemberCommandService familyMemberCommandService;
    private final FamilyMemberQueryService familyMemberQueryService;

    public FamilyMemberController(FamilyMemberCommandService familyMemberCommandService,
                                  FamilyMemberQueryService familyMemberQueryService) {
        this.familyMemberCommandService = familyMemberCommandService;
        this.familyMemberQueryService = familyMemberQueryService;
    }

    @PostMapping
    public ResponseEntity<FamilyMember> createFamilyMember(@RequestBody FamilyMember familyMember) {
        return ResponseEntity.ok(familyMemberCommandService.createFamilyMember(familyMember));
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
    public ResponseEntity<FamilyMember> updateFamilyMember(@PathVariable Long id, @RequestBody FamilyMember familyMember) {
        return ResponseEntity.ok(familyMemberCommandService.updateFamilyMember(id, familyMember));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFamilyMember(@PathVariable Long id) {
        familyMemberCommandService.deleteFamilyMember(id);
        return ResponseEntity.noContent().build();
    }
}
