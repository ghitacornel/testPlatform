package contracts.clients;

import commons.model.IdResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
public interface ClientContract {

    @GetMapping("client/count")
    long count();

    @GetMapping(value = "client", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ClientDetailsResponse> findAll();

    @GetMapping(value = "client/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ClientDetailsResponse findById(@PathVariable("id") Integer id);

    @PostMapping(value = "client", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    IdResponse register(@RequestBody ClientRegisterRequest inputModel);

    @PatchMapping("client/unregister/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void unregister(@Valid @NotNull @PathVariable("id") Integer id);

}
