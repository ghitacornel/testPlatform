package companies.service;

import commons.model.IdResponse;
import companies.mapper.CompanyMapper;
import companies.repository.CompanyRepository;
import companies.repository.entity.Company;
import contracts.companies.CompanyRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository repository;
    private final CompanyMapper mapper;

    public IdResponse register(CompanyRegisterRequest request) {
        Company entity = mapper.map(request);
        repository.save(entity);
        log.info("registered {}", entity);
        return new IdResponse(entity.getId());
    }

    public void retiring(Integer id) {
        Company company = repository.findById(id).orElse(null);
        if (company == null) {
            log.warn("company not found {}", id);
            return;
        }
        company.retiring();
        log.info("retiring {}", id);
    }

    public void retired(Integer id) {
        Company company = repository.findById(id).orElse(null);
        if (company == null) {
            log.warn("company not found {}", id);
            return;
        }
        company.retired();
        log.info("retired {}", id);
    }

}
