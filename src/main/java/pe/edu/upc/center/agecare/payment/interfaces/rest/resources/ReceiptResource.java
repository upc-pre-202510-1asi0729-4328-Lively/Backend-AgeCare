package pe.edu.upc.center.resident.payment.interfaces.rest.resources;

import java.util.Date;

public record ReceiptResource(Long receiptId,
                              Date issueDate,
                              Date dueDate,
                              Float totalAmount,
                              Boolean status,
                              Long residentId,
                              Long paymentId,
                              Date paymentDate,
                              Float amountPaid,
                              Long paymentMethod,
                              String type
){}
