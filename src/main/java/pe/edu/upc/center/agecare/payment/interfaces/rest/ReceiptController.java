package pe.edu.upc.center.agecare.payment.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.agecare.payment.domain.model.commands.DeleteReceiptCommand;
import pe.edu.upc.center.agecare.payment.domain.model.queries.GetAllReceiptsQuery;
import pe.edu.upc.center.agecare.payment.domain.model.queries.GetReceiptByResidentIdQuery;
import pe.edu.upc.center.agecare.payment.domain.model.queries.GetReceiptByDateQuery;
import pe.edu.upc.center.agecare.payment.domain.model.valueobjects.ResidentId;
import pe.edu.upc.center.agecare.payment.domain.services.ReceiptQueryService;
import pe.edu.upc.center.agecare.payment.domain.services.ReceiptCommandService;
import pe.edu.upc.center.agecare.payment.interfaces.rest.resources.CreateReceiptResource;
import pe.edu.upc.center.agecare.payment.interfaces.rest.resources.ReceiptResource;
import pe.edu.upc.center.agecare.payment.interfaces.rest.transform.CreateReceiptCommandFromResourceAssembler;
import pe.edu.upc.center.agecare.payment.interfaces.rest.transform.ReceiptResourceFromEntityAssembler;
import pe.edu.upc.center.agecare.payment.interfaces.rest.transform.UpdateReceiptCommandFromResourceAssembler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/receipts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Receipts", description = "Receipt Management Endpoints")
public class ReceiptController {
    private final ReceiptQueryService receiptQueryService;
    private final ReceiptCommandService receiptCommandService;

    public ReceiptController(ReceiptQueryService receiptQueryService, ReceiptCommandService receiptCommandService) {
        this.receiptQueryService = receiptQueryService;
        this.receiptCommandService = receiptCommandService;
    }

    @PostMapping
    public ResponseEntity<ReceiptResource> createReceipt(@RequestBody CreateReceiptResource resource) {
        var createCommand = CreateReceiptCommandFromResourceAssembler.toCommandFromResource(resource);
        var residentId = receiptCommandService.handle(createCommand);

        if (residentId == null || residentId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getReceiptByResidentIdQuery = new GetReceiptByResidentIdQuery(residentId);
        var optionalReceipt = receiptQueryService.handle(getReceiptByResidentIdQuery);

        if (optionalReceipt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var receiptResource = ReceiptResourceFromEntityAssembler.toResourceFromEntity(optionalReceipt.get());
        return new ResponseEntity<>(receiptResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReceiptResource>> getAllReceipts() {
        var query = new GetAllReceiptsQuery();
        var receipts = receiptQueryService.handle(query);
        var resources = receipts.stream()
                .map(ReceiptResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{residentId}")
    public ResponseEntity<ReceiptResource> getReceiptByResidentId(@PathVariable Long residentId) {
        var query = new GetReceiptByResidentIdQuery(residentId);
        var optionalReceipt = receiptQueryService.handle(query);

        if (optionalReceipt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var resource = ReceiptResourceFromEntityAssembler.toResourceFromEntity(optionalReceipt.get());
        return ResponseEntity.ok(resource);
    }

    @PutMapping("/{receiptId}")
    public ResponseEntity<ReceiptResource> updateReceipt(@PathVariable Long receiptId, @RequestBody ReceiptResource resource) {
        var updateCommand = UpdateReceiptCommandFromResourceAssembler.toCommandFromResource(receiptId, resource);
        var optionalReceipt = receiptCommandService.handle(updateCommand);

        if (optionalReceipt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var updatedResource = ReceiptResourceFromEntityAssembler.toResourceFromEntity(optionalReceipt.get());
        return ResponseEntity.ok(updatedResource);
    }

    @DeleteMapping("/{receiptId}")
    public ResponseEntity<?> deleteReceipt(@PathVariable Long receiptId) {
        var deleteCommand = new DeleteReceiptCommand(receiptId);
        receiptCommandService.handle(deleteCommand);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/searchById")
    public ResponseEntity<ReceiptResource> getReceiptByDate(@RequestParam Date issueDate) {
        var query = new GetReceiptByDateQuery(issueDate);
        var optionalReceipt = receiptQueryService.handle(query);

        if (optionalReceipt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resource = ReceiptResourceFromEntityAssembler.toResourceFromEntity(optionalReceipt.get());
        return ResponseEntity.ok(resource);
    }
}
