package companies.controller;

import companies.controller.model.request.CompanyRegisterRequest;
import companies.controller.model.response.CompanyDetailsResponse;
import companies.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/company")
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

    @GetMapping(value = "{id}")
    public CompanyDetailsResponse findById(@Valid @NotNull @PathVariable(name = "id") Integer id) {
        return service.findById(id);
    }

    @PostMapping
    public CompanyDetailsResponse register(@Valid @RequestBody CompanyRegisterRequest json) {
        return service.register(json);
    }

    @DeleteMapping(value = "{id}")
    public void deleteById(@Valid @NotNull @PathVariable(name = "id") Integer id) {
        service.deleteById(id);
    }

}
