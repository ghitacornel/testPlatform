package companies.service;

import companies.controller.model.response.CompanyStatistics;
import companies.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StatisticsService {

    private final CompanyRepository repository;

    public CompanyStatistics getStatistics() {
        return CompanyStatistics.builder()
                .countAll(repository.count())
                .build();
    }

}
