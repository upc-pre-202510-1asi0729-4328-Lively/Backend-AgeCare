package pe.edu.upc.center.resident.payment.interfaces.rest.transform;

import pe.edu.upc.center.resident.payment.domain.model.commands.CreateReceiptCommand;
import pe.edu.upc.center.resident.payment.domain.model.valueobjects.ResidentId;
import pe.edu.upc.center.resident.payment.interfaces.rest.resources.CreateReceiptResource;


public class CreateReceiptCommandFromResourceAssembler {
    public static CreateReceiptCommand toCommandFromResource(CreateReceiptResource resource) {
        ResidentId residentId = new ResidentId(resource.residentID());

        return new CreateReceiptCommand(
                //resource.rec,
                resource.issueDate(),
                resource.dueDate(),
                resource.totalAmount(),
                resource.status(),
                residentId,
                resource.paymentID(),
                resource.paymentDate(),
                resource.amountPaid(),
                resource.paymentMethod(),
                resource.type()
        );
    }
}
