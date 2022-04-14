package companies.services;

import companies.services.mappers.CompanyMapper;
import companies.controllers.models.CompanyDto;
import companies.controllers.models.CompanyRegisterRequest;
import companies.repositories.CompanyRepository;
import companies.repositories.entities.Company;
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

    public List<CompanyDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public CompanyDto findByName(String name) {
        return repository.findByName(name)
                .map(mapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Company with name " + name + " not found"));
    }

    public CompanyDto register(CompanyRegisterRequest request) {
        if (repository.findByName(request.getName()).isPresent()) {
            throw new ValidationException("username taken");
        }
        Company company = new Company();
        company.setName(request.getName());
        company.setIndustry(request.getIndustry());
        company.setUrl(request.getUrl());
        repository.save(company);
        return mapper.map(company);
    }

    public void unregister(String name) {
        repository.findByName(name).ifPresent(repository::delete);
    }

}
