package companies.service;

import commons.model.IdResponse;
import companies.exceptions.CompanyNotFoundException;
import companies.mapper.CompanyMapper;
import companies.repository.CompanyRepository;
import companies.repository.entity.Company;
import companies.repository.entity.Status;
import contracts.companies.CompanyDetailsResponse;
import contracts.companies.CompanyRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository repository;
    private final CompanyMapper mapper;

    public List<CompanyDetailsResponse> findAll() {
        return repository.findByStatus(Status.ACTIVE).stream()
                .map(mapper::map)
                .toList();
    }

    public List<Integer> findActiveIds() {
        return repository.findActiveIds();
    }

    public List<Integer> findRetiredIds() {
        return repository.findRetiredIds();
    }

    public CompanyDetailsResponse findById(Integer id) {
        return repository.findById(id)
                .map(mapper::map)
                .orElseThrow(() -> new CompanyNotFoundException(id));
    }

    public IdResponse register(CompanyRegisterRequest request) {
        Company entity = mapper.map(request);
        repository.save(entity);
        log.info("registered {}", entity);
        return new IdResponse(entity.getId());
    }

    public long count() {
        return repository.countByStatus(Status.ACTIVE);
    }

    public void retire(Integer id) {
        Company company = repository.findById(id).orElse(null);
        if (company == null) {
            log.warn("company not found {}", id);
            return;
        }
        company.retire();
        log.info("unregistered {}", id);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
        log.info("deleted {}", id);
    }
}
