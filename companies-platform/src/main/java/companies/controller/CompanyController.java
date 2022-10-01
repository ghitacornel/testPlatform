package companies.controller;

import companies.controller.model.request.CompanyRegisterRequest;
import companies.controller.model.response.CompanyDetailsResponse;
import companies.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
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

    @GetMapping(value = "{id}")
    public CompanyDetailsResponse findById(@Validated @NotNull @PathVariable(name = "id") Integer id) {
        return service.findById(id);
    }

    @PostMapping
    public CompanyDetailsResponse register(@Validated @RequestBody CompanyRegisterRequest json) {
        return service.register(json);
    }

    @DeleteMapping(value = "{id}")
    public void deleteById(@Validated @NotNull @PathVariable(name = "id") Integer id) {
        service.deleteById(id);
    }

}
