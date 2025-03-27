package clients.controller;

import clients.service.ClientSearchService;
import clients.service.ClientService;
import commons.model.IdResponse;
import contracts.clients.ClientContract;
import contracts.clients.ClientDetailsResponse;
import contracts.clients.ClientRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ClientController implements ClientContract {

    private final ClientService service;
    private final ClientSearchService searchService;

    public List<ClientDetailsResponse> findAll() {
        return searchService.findAll();
    }

    public long count() {
        return searchService.countAllActive();
    }

    public List<Integer> findActiveIds() {
        return searchService.findActiveIds();
    }

    public List<Integer> findRetiredIds() {
        return searchService.findRetiredIds();
    }

    public ClientDetailsResponse findById(Integer id) {
        return searchService.findById(id);
    }

    public IdResponse register(ClientRegisterRequest request) {
        return service.register(request);
    }

    public void retire(Integer id) {
        service.retire(id);
    }

    public void delete(Integer id) {
        service.delete(id);
    }

}
