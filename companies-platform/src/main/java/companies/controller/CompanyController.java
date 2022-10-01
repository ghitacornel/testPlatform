package companies.controller;

import companies.controller.model.response.CompanyDetailsResponse;
import companies.controller.model.request.CompanyRegisterRequest;
import companies.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
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

    @GetMapping(value = "{name}")
    public CompanyDetailsResponse findByName(@PathVariable(name = "name") String name) {
        return service.findByName(name);
    }

    @PostMapping
    public CompanyDetailsResponse register(@Validated @RequestBody CompanyRegisterRequest json) {
        return service.register(json);
    }

    @DeleteMapping(value = "{name}")
    public void unregister(@Validated @NotBlank @PathVariable(name = "name") String name) {
        service.unregister(name);
    }

}
