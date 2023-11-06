package companies.service;

import commons.exceptions.ResourceNotFound;
import commons.model.IdResponse;
import companies.controller.model.request.CompanyRegisterRequest;
import companies.controller.model.response.CompanyDetailsResponse;
import companies.controller.model.response.CompanyStatistics;
import companies.repository.CompanyRepository;
import companies.repository.entity.Company;
import companies.mapper.CompanyMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository repository;
    private final CompanyMapper mapper;

    public List<CompanyDetailsResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public CompanyDetailsResponse findById(Integer id) {
        return repository.findById(id)
                .map(mapper::map)
                .orElseThrow(() -> new ResourceNotFound("Company with id " + id + " not found"));
    }

    public IdResponse register(CompanyRegisterRequest request) {
        Company company = mapper.map(request);
        repository.save(company);
        return new IdResponse(company.getId());
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public long count() {
        return repository.count();
    }

    public CompanyStatistics getStatistics() {
        return CompanyStatistics.builder()
                .countAll(repository.count())
                .build();
    }
}
