package flows.controller;

import commons.model.IdResponse;
import contracts.flows.FlowsContract;
import contracts.orders.CreateOrderRequest;
import flows.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class FlowsController implements FlowsContract {

    private final OrderService orderService;
    private final CompanyService companyService;
    private final ClientService clientService;

    public IdResponse createOrder(CreateOrderRequest request) {
        return orderService.create(request);
    }

    public void completeOrder(Integer id) {
        orderService.complete(id);
    }

    public void cancelOrder(Integer id) {
        orderService.cancel(id);
    }

    public void deleteCompany(Integer id) {
        companyService.deleteCompany(id);
    }

    @Override
    public void deleteClient(Integer id) {
        clientService.deleteClient(id);
    }

}
