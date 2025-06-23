package pe.edu.upc.center.agecare.payment.domain.services;

import pe.edu.upc.center.agecare.payment.domain.model.aggregates.Receipt;
import pe.edu.upc.center.resident.payment.domain.model.commands.CreateReceiptCommand;
import pe.edu.upc.center.resident.payment.domain.model.commands.DeleteReceiptCommand;
import pe.edu.upc.center.resident.payment.domain.model.commands.UpdateReceiptCommand;

import java.util.Optional;

public interface ReceiptCommandService {
    Long handle(CreateReceiptCommand command);
    Optional<Receipt> handle(UpdateReceiptCommand command);
    void handle(DeleteReceiptCommand command);
}
