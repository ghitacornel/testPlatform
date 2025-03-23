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
        return service.countAllActive();
    }

    public List<Integer> findActiveIds() {
        return service.findActiveIds();
    }

    public List<Integer> findRetiredIds() {
        return service.findRetiredIds();
    }

    public ClientDetailsResponse findById(Integer id) {
        return service.findById(id);
    }

    @Counted
    public IdResponse register(ClientRegisterRequest request) {
        return service.register(request);
    }

    @Counted
    public void retire(Integer id) {
        service.retire(id);
    }

    @Counted
    public void delete(Integer id) {
        service.delete(id);
    }

}
