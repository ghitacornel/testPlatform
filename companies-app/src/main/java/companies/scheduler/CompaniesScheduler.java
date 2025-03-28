package companies.scheduler;

import companies.repository.CompanyRepository;
import companies.repository.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CompaniesScheduler {

    private final CompanyRepository repository;

    @Scheduled(fixedRate = 10000)
    void deleteRetired() {
        repository.deleteByStatus(Status.RETIRED);
    }

}

