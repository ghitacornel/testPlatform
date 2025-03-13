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

    @GetMapping("companies/count")
    long count();

    @GetMapping(value = "companies", produces = MediaType.APPLICATION_JSON_VALUE)
    List<CompanyDetailsResponse> findAll();

    @GetMapping(value = "companies/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    CompanyDetailsResponse findById(@Valid @NotNull @PathVariable("id") Integer id);

    @PostMapping(value = "companies", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    IdResponse register(@Valid @RequestBody CompanyRegisterRequest inputModel);

    @PatchMapping("companies/unregister/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void unregister(@Valid @NotNull @PathVariable("id") Integer id);

    @GetMapping("companies/ids/active")
    List<Integer> findActiveIds();

    @GetMapping("companies/ids/retired")
    List<Integer> findRetiredIds();

    @DeleteMapping("companies/{id}")
    void delete(@Valid @NotNull @PathVariable("id") Integer id);

}
