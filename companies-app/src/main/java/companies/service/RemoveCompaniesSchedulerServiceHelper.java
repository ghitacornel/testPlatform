package companies.service;

import companies.mapper.CompanyMapper;
import companies.repository.CompanyArchiveRepository;
import companies.repository.CompanyRepository;
import companies.repository.entity.Company;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class RemoveCompaniesSchedulerServiceHelper {

    private final CompanyRepository repository;
    private final CompanyMapper mapper;
    private final CompanyArchiveRepository archiveRepository;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Async
    public void delete(Company company) {
        archiveRepository.save(mapper.mapToArchive(company));
        repository.deleteById(company.getId());
        log.info("Deleted {}", company.getId());
    }

}
