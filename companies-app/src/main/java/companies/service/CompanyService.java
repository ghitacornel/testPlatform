package companies.service;

import commons.exceptions.ResourceNotFound;
import commons.model.IdResponse;
import companies.controller.model.request.CompanyRegisterRequest;
import companies.controller.model.response.CompanyDetailsResponse;
import companies.mapper.CompanyMapper;
import companies.repository.CompanyRepository;
import companies.repository.entity.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
        Company entity = mapper.map(request);
        repository.save(entity);
        log.info("registered " + entity);
        return new IdResponse(entity.getId());
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
        log.info("unregistered " + id);
    }

    public long count() {
        return repository.count();
    }

}
