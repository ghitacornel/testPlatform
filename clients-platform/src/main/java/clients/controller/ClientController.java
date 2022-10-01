package clients.controller;

import clients.controller.model.request.ClientRegisterRequest;
import clients.controller.model.response.ClientDetailsResponse;
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

    @GetMapping(value = "{id}")
    public ClientDetailsResponse findById(@Validated @NotBlank @PathVariable(name = "id") Integer id) {
        return service.findById(id);
    }

    @PostMapping
    public ClientDetailsResponse register(@Validated @RequestBody ClientRegisterRequest request) {
        return service.register(request);
    }

    @DeleteMapping(value = "{id}")
    public void deleteById(@Validated @NotBlank @PathVariable(name = "id") Integer id) {
        service.deleteById(id);
    }

}
