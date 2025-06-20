package pe.edu.upc.center.resident.payment.domain.services;


import pe.edu.upc.center.resident.payment.domain.model.aggregates.Receipt;
import pe.edu.upc.center.resident.payment.domain.model.queries.GetAllReceiptsQuery;
import pe.edu.upc.center.resident.payment.domain.model.queries.GetReceiptByDateQuery;
import pe.edu.upc.center.resident.payment.domain.model.queries.GetReceiptByResidentIdQuery;

import java.util.List;
import java.util.Optional;

public interface ReceiptQueryService {
    List<Receipt> handle(GetAllReceiptsQuery query);
    Optional<Receipt> handle(GetReceiptByResidentIdQuery query);
    Optional<Receipt> handle(GetReceiptByDateQuery query);
}
