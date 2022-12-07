package clients.controller;

import clients.controller.model.request.ClientRegisterRequest;
import clients.controller.model.response.ClientDetailsResponse;
import clients.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
@Validated
public class ClientController {
    private final ClientService service;

    @GetMapping
    public List<ClientDetailsResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("count")
    public long count() {
        return service.count();
    }

    @GetMapping(value = "{id}")
    public ClientDetailsResponse findById(@Valid @NotNull @PathVariable(name = "id") Integer id) {
        return service.findById(id);
    }

    @PostMapping
    public ClientDetailsResponse register(@Valid @RequestBody ClientRegisterRequest request) {
        return service.register(request);
    }

    @DeleteMapping(value = "{id}")
    public void deleteById(@Valid @NotNull @PathVariable(name = "id") Integer id) {
        service.deleteById(id);
    }

}
