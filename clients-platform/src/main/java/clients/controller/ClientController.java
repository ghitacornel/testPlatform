package clients.controller;

import clients.controller.model.ClientDto;
import clients.controller.model.ClientRegisterRequest;
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
    public List<ClientDto> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "{name}")
    public ClientDto findByName(@Validated @NotBlank @PathVariable(name = "name") String name) {
        return service.findByName(name);
    }

    @PostMapping
    public ClientDto register(@Validated @RequestBody ClientRegisterRequest request) {
        return service.register(request);
    }

    @DeleteMapping(value = "{name}")
    public void unregister(@Validated @NotBlank @PathVariable(name = "name") String name) {
        service.unregister(name);
    }

}
