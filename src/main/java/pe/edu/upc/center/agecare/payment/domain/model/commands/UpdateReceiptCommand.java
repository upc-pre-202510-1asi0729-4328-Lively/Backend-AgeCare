package pe.edu.upc.center.agecare.payment.domain.model.commands;

import pe.edu.upc.center.agecare.payment.domain.model.valueobjects.ResidentId;
import java.util.Date;

public record UpdateReceiptCommand(
        Long receiptId,
        Date issueDate,
        Date dueDate,
        Float totalAmount,
        Boolean status,
        ResidentId residentId,
        Long paymentID,
        Date paymentDate,
        Float amountPaid,
        Long paymentMethod,
        String type
) {}
