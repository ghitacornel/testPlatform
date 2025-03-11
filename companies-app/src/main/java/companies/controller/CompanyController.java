package companies.controller;

import commons.model.IdResponse;
import companies.service.CompanyService;
import contracts.companies.CompanyContract;
import contracts.companies.CompanyDetailsResponse;
import contracts.companies.CompanyRegisterRequest;
import io.micrometer.core.annotation.Counted;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CompanyController implements CompanyContract {

    private final CompanyService service;

    public List<CompanyDetailsResponse> findAll() {
        return service.findAll();
    }

    public long count() {
        return service.count();
    }

    public CompanyDetailsResponse findById(Integer id) {
        return service.findById(id);
    }

    @Counted(description = "companies registered")
    public IdResponse register(CompanyRegisterRequest request) {
        return service.register(request);
    }

    @Counted(description = "companies deleted")
    public void delete(Integer id) {
        service.delete(id);
    }

    @Counted(description = "companies unregistered")
    public void unregister(Integer id) {
        service.unregister(id);
    }

}
