package companies.controllers;

import companies.controllers.models.CompanyDto;
import companies.controllers.models.CompanyRegisterRequest;
import companies.services.CompanyService;
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

    @GetMapping("/all")
    public List<CompanyDto> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "{name}")
    public CompanyDto findByName(@PathVariable(name = "name") String name) {
        return service.findByName(name);
    }

    @PostMapping(value = "/register")
    public CompanyDto register(@Validated @RequestBody CompanyRegisterRequest json) {
        return service.register(json);
    }

    @DeleteMapping(value = "{name}")
    public void unregister(@Validated @NotBlank @PathVariable(name = "name") String name) {
        service.unregister(name);
    }

}
