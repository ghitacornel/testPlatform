package companies.service;

import companies.controller.model.request.CompanyRegisterRequest;
import companies.controller.model.response.CompanyDetailsResponse;
import companies.repository.CompanyRepository;
import companies.repository.entity.Company;
import companies.service.mapper.CompanyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
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

    public CompanyDetailsResponse findByName(String name) {
        return repository.findByName(name)
                .map(mapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Company with name " + name + " not found"));
    }

    public CompanyDetailsResponse register(CompanyRegisterRequest request) {
        if (repository.findByName(request.getName()).isPresent()) {
            throw new ValidationException("company name taken");
        }
        Company company = mapper.map(request);
        repository.save(company);
        return mapper.map(company);
    }

    public void unregister(String name) {
        Company company = repository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Company with name " + name + " not found"));
        repository.delete(company);
    }

}
