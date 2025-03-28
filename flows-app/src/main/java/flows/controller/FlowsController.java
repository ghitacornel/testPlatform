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
    private final ProductService productService;

    public IdResponse createOrder(CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    public void cancelOrder(Integer id) {
        orderService.cancelOrder(id);
    }

    public void deleteCompany(Integer id) {
        companyService.deleteCompany(id);
    }

    public void deleteClient(Integer id) {
        clientService.deleteClient(id);
    }

    public void cancelProduct(Integer id) {
        productService.cancelProduct(id);
    }

}
