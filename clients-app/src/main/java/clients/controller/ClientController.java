package clients.controller;

import clients.service.ClientService;
import commons.model.IdResponse;
import contracts.clients.ClientContract;
import contracts.clients.ClientDetailsResponse;
import contracts.clients.ClientRegisterRequest;
import io.micrometer.core.annotation.Counted;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ClientController implements ClientContract {

    private final ClientService service;

    public List<ClientDetailsResponse> findAll() {
        return service.findAll();
    }

    public long count() {
        return service.count();
    }

    public ClientDetailsResponse findById(Integer id) {
        return service.findById(id);
    }

    @Counted(value = "registered", description = "clients registered")
    public IdResponse register(ClientRegisterRequest request) {
        return service.register(request);
    }

    @Counted(value = "deleted", description = "clients deleted")
    public void delete(Integer id) {
        service.deleteById(id);
    }

    @Counted(value = "unregistered", description = "clients unregistered")
    public void unregister(Integer id) {
        service.unregister(id);
    }

}
