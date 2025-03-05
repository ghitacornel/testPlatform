package invoices.service;

import commons.model.Statistics;
import invoices.repository.InvoiceRepository;
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
                .build();
    }

}
