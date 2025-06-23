package pe.edu.upc.center.agecare.payment.application.Internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.agecare.payment.domain.model.aggregates.Receipt;
import pe.edu.upc.center.agecare.payment.domain.model.queries.GetAllReceiptsQuery;
import pe.edu.upc.center.agecare.payment.domain.model.queries.GetReceiptByDateQuery;
import pe.edu.upc.center.agecare.payment.domain.model.queries.GetReceiptByReceiptIdQuery;
import pe.edu.upc.center.agecare.payment.domain.model.queries.GetReceiptByResidentIdQuery;
import pe.edu.upc.center.agecare.payment.domain.services.ReceiptQueryService;
import pe.edu.upc.center.agecare.payment.infrastructure.persistence.jpa.repositories.ReceiptRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReceiptQueryServiceImpl implements ReceiptQueryService{
    private final ReceiptRepository receiptRepository;

    public ReceiptQueryServiceImpl(ReceiptRepository receiptRepository){
        this.receiptRepository = receiptRepository;
    }

    @Override
    public List<Receipt> handle(GetAllReceiptsQuery query) {return receiptRepository.findAll();}

    @Override
    public Optional<Receipt> handle(GetReceiptByResidentIdQuery query){
        return receiptRepository.findById(query.id());
    }

    @Override
    public Optional<Receipt> handle(GetReceiptByDateQuery query){
        return receiptRepository.findByPaymentDate(query.date());
    }

    @Override
    public Optional<Receipt> handle(GetReceiptByReceiptIdQuery query){
        return receiptRepository.findById(query.receiptId());
    }
}
