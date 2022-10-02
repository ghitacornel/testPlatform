package companies.scheduler;

import companies.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DeleteCompaniesScheduler {

    private final CompanyRepository repository;

    @Scheduled(fixedRate = 10000)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteAllMarked() {
        repository.deleteAllMarked();
    }

}
