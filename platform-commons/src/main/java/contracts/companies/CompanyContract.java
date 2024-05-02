package contracts.companies;

import commons.model.IdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CompanyContract {

    @GetMapping("company/count")
    long count();

    @GetMapping(value = "company", produces = MediaType.APPLICATION_JSON_VALUE)
    List<CompanyDetailsResponse> findAll();

    @GetMapping(value = "company/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    CompanyDetailsResponse findById(@PathVariable("id") Integer id);

    @PostMapping(value = "company", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    IdResponse create(@RequestBody CompanyRegisterRequest inputModel);

    @DeleteMapping("company/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable("id") Integer id);

    @PatchMapping("company/retire/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void retire(@PathVariable("id") Integer id);

}
