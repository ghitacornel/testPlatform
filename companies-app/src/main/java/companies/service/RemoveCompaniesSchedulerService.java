package companies.service;

import companies.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemoveCompaniesSchedulerService {

    private final CompanyRepository repository;
    private final RemoveCompaniesSchedulerServiceHelper helper;

    public void removeRetiredCompanies() {
        repository.findAllRetired().forEach(helper::delete);
    }

}
