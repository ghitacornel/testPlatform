package companies.service;

import companies.controller.model.request.CompanyRegisterRequest;
import companies.controller.model.response.CompanyDetailsResponse;
import companies.exception.CompanyNotFoundException;
import companies.jms.JMSProducerQueue;
import companies.repository.CompanyRepository;
import companies.repository.entity.Company;
import companies.service.mapper.CompanyMapper;
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
    private final JMSProducerQueue jmsProducerQueue;

    public List<CompanyDetailsResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public CompanyDetailsResponse findById(Integer id) {
        return repository.findById(id)
                .map(mapper::map)
                .orElseThrow(() -> new CompanyNotFoundException(id));
    }

    public CompanyDetailsResponse register(CompanyRegisterRequest request) {
        Company company = mapper.map(request);
        repository.save(company);
        return mapper.map(company);
    }

    public void deleteById(Integer id) {
        jmsProducerQueue.sendDeleteMessage(id);
        repository.deleteById(id);
    }

    public long count() {
        return repository.count();
    }
}
