package invoices.service;

import commons.model.Statistics;
import invoices.repository.InvoiceRepository;
import invoices.repository.entity.InvoiceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StatisticsService {

    private final InvoiceRepository repository;

    public Statistics getStatistics() {
        return Statistics.builder()
                .countAll(repository.count())
                .countNew(repository.countByStatus(InvoiceStatus.NEW))
                .countCompleted(repository.countByStatus(InvoiceStatus.COMPLETED))
                .countError(repository.countByStatus(InvoiceStatus.ERROR))
                .build();
    }

}
