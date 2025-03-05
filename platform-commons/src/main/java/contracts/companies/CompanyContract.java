package contracts.companies;

import commons.model.IdResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
public interface CompanyContract {

    @GetMapping("company/count")
    long count();

    @GetMapping(value = "company", produces = MediaType.APPLICATION_JSON_VALUE)
    List<CompanyDetailsResponse> findAll();

    @GetMapping(value = "company/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    CompanyDetailsResponse findById(@Valid @NotNull @PathVariable("id") Integer id);

    @PostMapping(value = "company", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    IdResponse create(@Valid @RequestBody CompanyRegisterRequest inputModel);

    @PatchMapping("company/retire/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void retire(@Valid @NotNull @PathVariable("id") Integer id);

    @DeleteMapping("company/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@Valid @NotNull @PathVariable("id") Integer id);

}
