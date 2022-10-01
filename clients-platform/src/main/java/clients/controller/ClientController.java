package clients.controller;

import clients.controller.model.response.ClientDetailsResponse;
import clients.controller.model.request.ClientRegisterRequest;
import clients.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
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

    @GetMapping(value = "{name}")
    public ClientDetailsResponse findByName(@Validated @NotBlank @PathVariable(name = "name") String name) {
        return service.findByName(name);
    }

    @PostMapping
    public ClientDetailsResponse register(@Validated @RequestBody ClientRegisterRequest request) {
        return service.register(request);
    }

    @DeleteMapping(value = "{name}")
    public void unregister(@Validated @NotBlank @PathVariable(name = "name") String name) {
        service.unregister(name);
    }

}
