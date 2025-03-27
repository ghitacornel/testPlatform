package companies.service;

import companies.exceptions.CompanyNotFoundException;
import companies.mapper.CompanyMapper;
import companies.repository.CompanyRepository;
import companies.repository.entity.Status;
import contracts.companies.CompanyDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanySearchService {

    private final CompanyRepository repository;
    private final CompanyMapper mapper;

    public List<CompanyDetailsResponse> findAll() {
        return repository.findByStatus(Status.ACTIVE).stream().map(mapper::map).toList();
    }

    public List<Integer> findActiveIds() {
        return repository.findActiveIds();
    }

    public List<Integer> findRetiredIds() {
        return repository.findRetiredIds();
    }

    public CompanyDetailsResponse findById(Integer id) {
        return repository.findById(id).map(mapper::map).orElseThrow(() -> new CompanyNotFoundException(id));
    }

    public long count() {
        return repository.countByStatus(Status.ACTIVE);
    }

}
