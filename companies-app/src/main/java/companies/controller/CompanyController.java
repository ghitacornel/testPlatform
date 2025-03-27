package companies.controller;

import commons.model.IdResponse;
import companies.service.CompanySearchService;
import companies.service.CompanyService;
import contracts.companies.CompanyContract;
import contracts.companies.CompanyDetailsResponse;
import contracts.companies.CompanyRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CompanyController implements CompanyContract {

    private final CompanyService service;
    private final CompanySearchService searchService;

    public List<CompanyDetailsResponse> findAll() {
        return searchService.findAll();
    }

    public long count() {
        return searchService.count();
    }

    public CompanyDetailsResponse findById(Integer id) {
        return searchService.findById(id);
    }

    public IdResponse register(CompanyRegisterRequest request) {
        return service.register(request);
    }

    public void retire(Integer id) {
        service.retire(id);
    }

    public List<Integer> findActiveIds() {
        return searchService.findActiveIds();
    }

    public List<Integer> findRetiredIds() {
        return searchService.findRetiredIds();
    }

    public void delete(Integer id) {
        service.delete(id);
    }

}
