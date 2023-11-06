package companies.controller;

import commons.model.IdResponse;
import companies.controller.model.request.CompanyRegisterRequest;
import companies.controller.model.response.CompanyDetailsResponse;
import companies.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@RestController
@RequestMapping("company")
@RequiredArgsConstructor
@Validated
public class CompanyController {

    private final CompanyService service;

    @GetMapping
    public List<CompanyDetailsResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("count")
    public long count() {
        return service.count();
    }

    @GetMapping("{id}")
    public CompanyDetailsResponse findById(@Valid @NotNull @PathVariable(name = "id") Integer id) {
        return service.findById(id);
    }

    @PostMapping
    public IdResponse create(@Valid @RequestBody CompanyRegisterRequest request) {
        return service.create(request);
    }

    @PatchMapping("/retire/{id}")
    public void retire(@Valid @NotNull @PathVariable(name = "id") Integer id) {
        service.retire(id);
    }

    @DeleteMapping("{id}")
    public void delete(@Valid @NotNull @PathVariable(name = "id") Integer id) {
        service.delete(id);
    }

}
